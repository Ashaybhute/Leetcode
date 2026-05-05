class Solution {
public:
    ListNode* rotateRight(ListNode* head, int k) {
        if(!head || !head->next || k==0) return head;
        
        
        ListNode* tail = head;
        int n = 1;
        while(tail->next){
            tail = tail->next;
            n++;
        }
        
       
        tail->next = head;
        
        
        k = k % n;
        int stepsToNewHead = n - k;
        
        
        ListNode* newTail = tail;
        while(stepsToNewHead--){
            newTail = newTail->next;
        }
        
        
        ListNode* newHead = newTail->next;
        newTail->next = NULL;
        
        return newHead;
    }
};