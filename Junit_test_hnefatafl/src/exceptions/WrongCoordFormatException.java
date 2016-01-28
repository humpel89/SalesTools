package exceptions;

/**
 * 
 * @author Hampus Carlbom
 * @author Daniel Carlström
 *
 */

@SuppressWarnings("serial")
public class WrongCoordFormatException extends RuntimeException {
    public WrongCoordFormatException (String msg)
    {
        super (msg); 
    }
}
