package exceptions;
/**
 * 
 * @author Hampus Carlbom
 * @author Daniel Carlström
 *
 */
@SuppressWarnings("serial")
public class NoMoveSetException extends Exception {
    public NoMoveSetException (String msg)
    {
        super (msg); 
    }
}
