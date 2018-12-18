import maincomponents.SearchComparator;

public class SearchComparatorTest {

    public static void main(String[] args){
        test();
    }

    private static void test(){

        SearchComparator sc = SearchComparator.getComparator("Kage");

        //Data set A
        assertEquals(0, sc.getScore("kage"));
        //Data set B
        assertEquals(0, sc.getScore("kage2"));
        //Data set C
        assertEquals(1, sc.getScore("kag"));
        //Data set D
        assertEquals(2, sc.getScore("kaeg"));
        //Data set E
        assertEquals(0, sc.getScore("kagee"));
        //Data set F
        assertEquals(3, sc.getScore("gaek"));
    }

    private static void assertEquals(int wanted, int result){
        if(wanted == result) System.out.println(wanted + "=" + result);
        else System.err.println(wanted + "=" + result);
    }

}
