package maincomponents;

import java.util.HashMap;

/***
 * This is a test class showing that after saving the watch list will be the same
 */
public class SaveTest {
    public static void main(String[] args) {
        HashMap<Integer, HashMap<Integer, HashMap<Integer, Integer>>> map = SaveTest.getMap();

        String[] seperators = new String[]{",", "o", "/", ":"};
        StringBuilder builder = new StringBuilder();
        // Series
        for (int i0: map.keySet()) {
            HashMap<Integer, HashMap<Integer, Integer>> m0 = map.get(i0);
            builder.append(i0);
            builder.append(seperators[1]);
            // Seasons
            for (int i1: m0.keySet()) {
                HashMap<Integer, Integer> m1 = m0.get(i1);
                builder.append(i1);
                builder.append(seperators[2]);
                // Episodes
                for (int i2: m1.keySet()){
                    int value = m1.get(i2);
                    builder.append(i2);
                    builder.append(seperators[3]);
                    // Duration
                    builder.append(value);

                    builder.append(seperators[2]);
                }
                builder.append(seperators[1]);
            }
            builder.append(seperators[0]);
        }
        //System.out.println(builder.toString());
        String content = builder.toString();


        String[] m0 = content.split(seperators[0]);
        HashMap<Integer, HashMap<Integer, HashMap<Integer, Integer>>> hm0 = new HashMap<>();
        //Series
        for (int i0 = 0; i0 < m0.length; i0++) {
            String s0 = m0[i0];
            String[] m1 = s0.split(seperators[1]);
            int id0 = Integer.parseInt(m1[0]);
            HashMap<Integer, HashMap<Integer, Integer>> hm1 = new HashMap<>();
            // Seasons
            for (int i1 = 1; i1 < m1.length; i1++) {
                String s1 = m1[i1];
                String[] m2 = s1.split(seperators[2]);
                int id1 = Integer.parseInt(m2[0]);
                HashMap<Integer, Integer> hm2 = new HashMap<>();
                // Episodes
                for (int i2 = 1; i2 < m2.length; i2++) {
                    String s2 = m2[i2];
                    String[] m3 = s2.split(seperators[3]);
                    int id3 = Integer.parseInt(m3[0]);
                    // Duration
                    int duration = Integer.parseInt(m3[1]);
                    hm2.put(id3, duration);
                }
                hm1.put(id1, hm2);
            }
            hm0.put(id0, hm1);
        }
        System.out.println(hm0.equals(map));
    }

    public static HashMap<Integer, HashMap<Integer, HashMap<Integer, Integer>>> getMap(){
        HashMap<Integer, HashMap<Integer, HashMap<Integer, Integer>>> map = new HashMap<>();

        for (int i0 = 0; i0 < 10; i0++) {
            HashMap<Integer, HashMap<Integer, Integer>> m0 = new HashMap<>();
            for (int i1 = 0; i1 < 10; i1++) {
                HashMap<Integer, Integer> m1 = new HashMap<>();
                for (int i2 = 0; i2 < 10; i2++) {
                    m1.put(i2, 2);
                }
                m0.put(i1, m1);
            }
            map.put(i0, m0);
        }
        return map;
    }
}
