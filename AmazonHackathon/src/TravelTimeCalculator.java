
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.amazon.fc.scheduler.hackathon.data.model.Coordinate;

/**
 * 
 *
 */
public class TravelTimeCalculator {
    private static final String LOCATION_ID_PREFIX = "P-1-";

    public static final double MAX_BINS_X = 2000;
    public static final double MAX_BINS_Y = 2000;
    
    private static final double BINS_PER_BAY = 100;
    private static final double BAYS_PER_SECTION = 10;
    
    private static final double BIN_TRAVEL_TIME = 0.2;
    private static final double BAY_TRAVEL_TIME = 0.2;
    private static final double ROW_TRAVEL_TIME = 2;
    private static final double AISLE_TRAVEL_TIME = 4;
    private static final double MOD_TRAVEL_TIME = 120;
    private static final long   TIME_AT_BIN = 3;
    
    public static String convertCoordinateToLocationID(int x, int y) {

        // MOD Location is based off of the quadrant on the coordinate plane
        //  D | A
        // ---+---
        //  C | B
        //
        String mod = (x >= 0 ? (y >= 0 ? "A" : "B") : (y >= 0 ? "D" : "C"));
        return LOCATION_ID_PREFIX + mod + "-" + String.format("%05d", Math.abs(x)) + String.format("%05d", Math.abs(y));
    }
    
    public static Coordinate convertLocationIDToCoordinate(String locationID) throws Exception {
        String patternStr = LOCATION_ID_PREFIX + "([ABCD])-(\\d{5})(\\d{5})";
        Pattern p = Pattern.compile(patternStr);
        Matcher m = p.matcher(locationID);
        
        if (! m.find()) {
            throw new Exception("Invalid LocationID: " + locationID);
        }
        
        String mod = m.group(1);
        int x = Integer.valueOf(m.group(2));
        int y = Integer.valueOf(m.group(3));
        
        Coordinate c = new Coordinate("A".equals(mod) || "B".equals(mod) ? x : -1 * x,
                                      "A".equals(mod) || "D".equals(mod) ? y : -1 * y);
        
        return c;
    }
    
    private static String getModForLocationID(String locationID) {
        return locationID.substring(LOCATION_ID_PREFIX.length(), LOCATION_ID_PREFIX.length() + 1);
    }
    
    public static long computeTravelTime(String locationID1, String locationID2) throws Exception {
        
        if (getModForLocationID(locationID1).equals(getModForLocationID(locationID2))) {
            return computeIntraModTravelTime(locationID1, locationID2) + TIME_AT_BIN;
        }
        else {
            return computeModToModTravelTime(getModForLocationID(locationID1), getModForLocationID(locationID2)) +
                   computeIntraModTravelTime(locationID1, convertCoordinateToLocationID(0, 0)) +
                   computeIntraModTravelTime(convertCoordinateToLocationID(0,  0), locationID2) + 
                   TIME_AT_BIN;
        }
    }
    
    private static long computeIntraModTravelTime(String locationID1, String locationID2) throws Exception {
        Coordinate c1 = convertLocationIDToCoordinate(locationID1);
        Coordinate c2 = convertLocationIDToCoordinate(locationID2);

        c1 = new Coordinate(Math.abs(c1.getX()), Math.abs(c1.getY()));
        c2 = new Coordinate(Math.abs(c2.getX()), Math.abs(c2.getY()));
        Coordinate section1 = new Coordinate(Math.floor(c1.getX()/BINS_PER_BAY), Math.floor(c1.getY()/BAYS_PER_SECTION));
        Coordinate section2 = new Coordinate(Math.floor(c2.getX()/BINS_PER_BAY), Math.floor(c2.getY()/BAYS_PER_SECTION));

        // Mostly just use Manhattan distance, with adjustments for crossing aisles/rows,
        // however, if the sections line up vertically, you need to know if you go left/right 
        // in order to get there fastest
        double xTT = (section1.getX() == section2.getX() &&
                c1.getY() !=
                c2.getY() ? Math.min((c1.getX() % BINS_PER_BAY) + (c2.getX() % BINS_PER_BAY),
                                        (BINS_PER_BAY - c1.getX() % BINS_PER_BAY)
                                        + (BINS_PER_BAY - c2.getX() % BINS_PER_BAY))
                    * BIN_TRAVEL_TIME
                : Math.abs(c1.getX() - c2.getX())
                * BIN_TRAVEL_TIME
                + Math.abs(section1.getX() - section2.getX())
                * AISLE_TRAVEL_TIME);

        // Nothing special if they line up horizontally
        double yTT = Math.abs(c1.getY() - c2.getY()) * BAY_TRAVEL_TIME + Math.abs(section1.getY() - section2.getY()) * ROW_TRAVEL_TIME;
        //System.out.println("xTT = " + xTT + ", yTT = " + yTT);
        return Math.round(xTT + yTT);
    }

    private static long computeModToModTravelTime(String mod1, String mod2) {
        return Math.round(MOD_TRAVEL_TIME);
    }

    /**
     * @param args
     */
    public static void main(String[] args) {
        try {
            String locationID1 = convertCoordinateToLocationID(2000,2000);
            String locationID2 = convertCoordinateToLocationID(-2000, -2000);
            long tt = computeTravelTime(locationID1, locationID2);
            System.out.println("TT from " + locationID1 + " to " + locationID2 + " is " + tt/60 + "  minutes");
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }
}
