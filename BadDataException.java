//Name: Yanxiang Wang
//USC loginid: yanxianw
//CS 455 PA4
//Fall 2015

import java.io.IOException;

/**
   This class reports bad input command.
*/
public class BadDataException extends IOException
{
   public BadDataException() {}
   public BadDataException(String message)
   {
      super(message);
   }
}
