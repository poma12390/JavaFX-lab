package lab6.common.exceptions;
/**
 * thrown when not enough permission to access the file
 */
public class FileWrongPermissionsException extends FileException{
    public FileWrongPermissionsException(String s){
        super(s);
    }
}