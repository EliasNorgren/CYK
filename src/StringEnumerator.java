

public class StringEnumerator {
    private final StringBuilder sb;
    private String appendString;
    private int increments;
    private Index insertIndex;

    public enum Index {
        BEGINNING,
        MIDDLE,
        END
    }

    public StringEnumerator(String startString, String appendString, Index insertIndex, int increments){
        sb = new StringBuilder(startString);
        this.appendString = appendString;
        this.increments = increments;
        this.insertIndex = insertIndex;
    }

    public String getNext(){
        for(int i = 0; i < increments; i++){
            if(this.insertIndex == Index.END){
                sb.append(appendString);
            }else if(this.insertIndex == Index.BEGINNING){
                sb.insert(0, appendString, 0, appendString.length());
            }else if(this.insertIndex == Index.MIDDLE){
                sb.insert(sb.toString().length() / 2, appendString, 0, appendString.length());
            }
        }
        return sb.toString();
    }

    public int getLength(){
        return sb.length();
    }
}
