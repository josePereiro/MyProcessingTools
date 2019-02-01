package Common;

import java.util.LinkedList;

public class _LinkedListTest {
    public static void main(String[] args) {

        LinkedList<String> linkedList = new LinkedList<>();
        for (int i = 0; i < 10; i++) {
            linkedList.add(System.nanoTime() + "");
        }

        for (int i = 0; i < 10; i++) {
            linkedList.add(System.nanoTime() + "");
            linkedList.removeFirst();
            System.out.println(linkedList.getLast());
            System.out.println(linkedList);
        }


    }

}
