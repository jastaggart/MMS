/*PLEASE DO NOT EDIT THIS CODE*/
/*This code was generated using the UMPLE 1.31.1.5860.78bb27cc6 modeling language!*/



// line 116 "model.ump"
// line 240 "model.ump"
public class RoomSize
{

  //------------------------
  // ENUMERATIONS
  //------------------------

  public enum Size { Small, Big }

  //------------------------
  // MEMBER VARIABLES
  //------------------------

  //RoomSize Attributes
  private Size size;

  //------------------------
  // CONSTRUCTOR
  //------------------------

  public RoomSize(Size aSize)
  {
    size = aSize;
  }

  //------------------------
  // INTERFACE
  //------------------------

  public boolean setSize(Size aSize)
  {
    boolean wasSet = false;
    size = aSize;
    wasSet = true;
    return wasSet;
  }

  public Size getSize()
  {
    return size;
  }

  public void delete()
  {}


  public String toString()
  {
    return super.toString() + "["+ "]" + System.getProperties().getProperty("line.separator") +
            "  " + "size" + "=" + (getSize() != null ? !getSize().equals(this)  ? getSize().toString().replaceAll("  ","    ") : "this" : "null");
  }
}