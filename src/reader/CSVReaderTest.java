package reader;



public class CSVReaderTest {

    public static void main(String[] args){
        CSVTest();
    }

    private static void CSVTest(){
        try {
            //Data set G
            int[] zero = new int[]{0};
            //Data set H
            int[] one = new int[]{1};
            //Data set I
            int[] two = new int[]{2};

            //Data set AG
            CSVReader reader = new CSVReader("res/test/testA.csv", zero);
            assertEquals(""+0, ""+reader.getContent().size());

            //Data set BG
            reader = new CSVReader("res/test/testB.csv", zero);
            assertEquals(""+0, ""+reader.getContent().size());

            //Data set CG
            reader = new CSVReader("res/test/testC.csv", zero);
            assertEquals(""+0, ""+reader.getContent().size());

            //Data set DG
            reader = new CSVReader("res/test/testD.csv", zero);
            assertEquals(""+0, ""+reader.getContent().size());

            //Data set EG
            reader = new CSVReader("res/test/testE.csv", zero);
            assertEquals(""+0, ""+reader.getContent().size());

            //Data set FG
            reader = new CSVReader("res/test/testF.csv", zero);
            assertEquals(""+0, ""+reader.getContent().size());

            //TODO:-------------------------------------------------------------

            //Data set AH
            reader = new CSVReader("res/test/testA.csv", one);
            assertEquals(""+0, ""+reader.getContent().size());

            //Data set BH
            reader = new CSVReader("res/test/testB.csv", one);
            assertEquals(""+0, ""+reader.getContent().size());

            //Data set CH
            reader = new CSVReader("res/test/testC.csv", one);
            assertEquals(""+0, ""+reader.getContent().size());

            //Data set DH
            reader = new CSVReader("res/test/testD.csv", one);
            assertEquals(""+1, ""+reader.getContent().size());

            //Data set EH
            reader = new CSVReader("res/test/testE.csv", one);
            assertEquals(""+1, ""+reader.getContent().size());

            //Data set FH
            reader = new CSVReader("res/test/testF.csv", one);
            assertEquals(""+2, ""+reader.getContent().size());

            //TODO:-------------------------------------------------------------

            //Data set AI
            reader = new CSVReader("res/test/testA.csv", two);
            assertEquals(""+0, ""+reader.getContent().size());

            //Data set BI
            reader = new CSVReader("res/test/testB.csv", two);
            assertEquals(""+0, ""+reader.getContent().size());

            //Data set CI
            reader = new CSVReader("res/test/testC.csv", two);
            assertEquals(""+0, ""+reader.getContent().size());

            //Data set DI
            reader = new CSVReader("res/test/testD.csv", two);
            assertEquals(""+0, ""+reader.getContent().size());

            //Data set EI
            reader = new CSVReader("res/test/testE.csv", two);
            assertEquals(""+0, ""+reader.getContent().size());

            //Data set FI
            reader = new CSVReader("res/test/testF.csv", two);
            assertEquals(""+0, ""+reader.getContent().size());

        } catch (Exception e){

        }
    }

    private static void assertEquals(String wanted, String result){
        if(wanted.equals(result)) System.out.println(wanted + " = " + result);
        else System.err.println(wanted + " = " + result);
    }
}
