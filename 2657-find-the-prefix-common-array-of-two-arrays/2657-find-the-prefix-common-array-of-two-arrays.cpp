class Solution {
public:
    vector<int> findThePrefixCommonArray(vector<int>& A, vector<int>& B) {
        int n = A.size();
        vector<int> ans(n);
        vector<bool> seenA(n+1,false), seenB(n+1,false);
        
        int common = 0;
        
        for(int i=0;i<n;i++){
            if(A[i] == B[i]){
                if(!seenA[A[i]]) common++;
            }
            else{
                if(seenB[A[i]]) common++;
                if(seenA[B[i]]) common++;
            }
            
            seenA[A[i]] = true;
            seenB[B[i]] = true;
            ans[i] = common;
        }
        
        return ans;
    }
};