#include <bits/stdc++.h>
using namespace std;
class node{
    public:
    int data;
    node* left;
    node* right;
    node(int x)
    {
        data = x;
        left  = NULL;
        right = NULL;
    }
};
/*node* mirror(node* root)
{

}*/
node* createtree()
{
    int x;
    cin>>x;
    if(x == -1)
     return NULL;
     node* n = new node(x);
    // n->data = x;
     n->left= createtree();
     n->right = createtree();
     return n;
}
void inorder(node* root)
{
    if(root==NULL)
     return;
     inorder(root->left);
     cout<<root->data<<endl;
     inorder(root->right);
}
void printArray(int arr[],int n)
{
    for(int i=0;i<n;i++)
    {
        cout<<arr[i]<<" ";
    }
    cout<<endl;
}
int k=0;
void roottoleaf(node* root,int path[],int k)
{
    if(root == NULL)
     return;
     path[k++] = root->data;
    if(!root->left && !root->right)
    {
      printArray(path,k);
      
    }
     roottoleaf(root->left,path,k);
     roottoleaf(root->right,path,k);
}
void kthlargestnode(node* root,int k,int &c)
{
    if(root == NULL || c>=k)
    {
        return;
    }
     
    kthlargestnode(root->right,k,c);
    c++;
    if(c==k)
     {cout<<root->data<<endl;
     return;
     }
    kthlargestnode(root->left,k,c); 
}
struct node* newNode(int data) 
{ 
    struct node* n = (struct node*) 
                         malloc(sizeof(struct node)); 
    n->data = data; 
    n->left = NULL; 
    n->right = NULL; 
      
    return(n); 
} 
  
void mirror(node* root)
{
    if(root==NULL)
    {
      return;
    }
    else
   { node* t;
    mirror(root->left);
    mirror(root->right);
     t = root->left;
    root->left = root->right;
    root->right = t;
    }//return root;
} 
/*
void mirror(struct node* n)  
{ 
    if (n == NULL)  
        return;  
    else
    { 
        struct node* temp; 
          
        /* do the subtrees */
       /* mirror(n->left); 
        mirror(n->right); 
      
        /* swap the pointers in this node 
        temp     = n->left; 
        n->left = n->right; 
        n->right = temp; 
    } 
}  */
int getlevel(node* root,int data)
{
    queue<node*> q;
    q.push(root);
    q.push(NULL);
    int level=1;
    while(!q.empty())
    {
        node* t = q.front();
        q.pop();
        
          
        if(t==NULL)
        {
            level++;
            if(q.front()!=NULL)
             q.push(NULL);
        } 
        else{
            if(t->data==data)
         return level;
        if(t->left)
         q.push(t->left);
        if(t->right)
         q.push(t->right); 
        }
    }
    return 0;
}
void printgivenlevel(node* root,int level,node* n)
{
    if(root == NULL || level < 2)
     return;
     if(level == 2)
     {
         if(root->left == n || root->right == n)
          return;
         if(root->left)
           cout<<root->data<<" ";
         if(root->right)
          cout<<root->right<<" ";   
     }
    else{ 
    printgivenlevel(root->left,level-1);
    printgivenlevel(root->right,level-1) }
}
int main() {
    //node* root = createtree();
    struct node *root = newNode(1); 
    root->left = newNode(2); 
    root->right = newNode(3); 
    root->left->left = newNode(4); 
    root->left->right = newNode(5);  
    inorder(root);
    int path[10000];
    cout<<"****************"<<endl;
    cout<<"Root to leaf paths"<<endl;

    roottoleaf(root,path,0);
    cout<<"****************"<<endl;
    cout<<"LEVEL:"<<getlevel(root,2)<<endl;
    int c = 0;
    cout<<"kth:";kthlargestnode(root,2,c);
    mirror(root);
    inorder(root);
}
