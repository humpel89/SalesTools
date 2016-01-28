package exceptions;

/**
 * 
 * @author Hampus Carlbom
 * @author Daniel Carlström
 *
 */

@SuppressWarnings("serial")
public class CoordinateOutOfBoundsException extends RuntimeException {
    public CoordinateOutOfBoundsException (String msg)
    {
        super (msg); 
    }
}
