import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.HashMap;

public class Solution {
    public class ListNode {
        public int val;
        public ListNode next;
        public ListNode(int val) { this.val = val; }
    }

    public ListNode removeDuplicates(ListNode head) {
        HashMap<Integer, Boolean> map = new HashMap<>();
        ListNode cur = head;
        ListNode prev = head;
        while (cur.next != null) {
            if (map.containsKey(cur.val)) {
                cur = removeNode(cur, prev);
            }
            else {
                map.put(cur.val, true);
                prev = cur;
                cur = cur.next;
            }
        }
        return head;
    }

    public ListNode removeNode(ListNode cur, ListNode prev) {
        prev.next = cur.next;
        cur = prev.next;
        return cur;
    }

    public void printList(ListNode head) {
        ListNode cur = head;
        while (cur.next != null) {
            System.out.print(cur.val);
            System.out.print("->");
            cur = cur.next;
        }
    }

    @Test
    public void testRemoveDuplicates()
    {
        final int SIZE = 20;
        ListNode head, cur, cur1;
        head = new ListNode(5);
        cur = head;
        for (int i=0; i<SIZE; i++) {
            ListNode node = new ListNode(i%6);
            cur.next = node;
            cur = cur.next;
        }
        cur.next = null;
        ListNode expected = new ListNode(5);
        cur = expected;
        for (int i=0; i<5; i++) {
            ListNode node = new ListNode(i%5);
            cur.next = node;
            cur = cur.next;
        }
        cur.next = null;
        printList(head);
        ListNode duplicatesRemoved = removeDuplicates(head);
        System.out.println();
        printList(duplicatesRemoved);
        cur = duplicatesRemoved;
        cur1 = expected;
        while (cur.next != null && cur1.next != null) {
            Assertions.assertEquals(cur.val, cur1.val);
            cur = cur.next;
            cur1 = cur1.next;
        }
    }
}
