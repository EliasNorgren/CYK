public class StringEnumerator {
    private final StringBuilder sb;
    String appendString;
    int increments;
    public StringEnumerator(String startString, String appendString, int increments){
        sb = new StringBuilder(startString);
        this.appendString = appendString;
        this.increments = increments;
    }

    public String getNext(){
        for(int i = 0; i < increments; i++){
            sb.append(appendString);
        }
        return sb.toString();
    }

    public int getLength(){
        return sb.length();
    }
}
