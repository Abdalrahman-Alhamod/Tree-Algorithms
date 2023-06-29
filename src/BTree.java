import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Queue;
import java.util.Scanner;

public class BTree {
    private Page rootPage;
    private final int rank;

    public BTree(int rank) {
        rootPage = null;
        this.rank = rank;
    }

    public boolean Search(Page page, int data) {
        if (page == null)
            return false;
        if (data < page.getMinValue())
            return Search(page.getLeftPage(), data);
        if (data > page.getMaxValue())
            return Search(page.getRightPage(), data);
        if (page.search(data))
            return true;
        return Search(page.getContentPage(data), data);
    }

    public Page Find(Page page, int data) {
        if (page == null)
            return null;
        if (data < page.getMinValue())
            return Find(page.getLeftPage(), data);
        if (data > page.getMaxValue())
            return Find(page.getRightPage(), data);
        if (page.search(data))
            return page;
        return Find(page.getContentPage(data), data);
    }

    public void Insert(Page page, int data) {
        if (Search(page, data))
            System.out.println("The Element " + data + " is Already Exist !");
        else
            InsertHelper(page, data);
    }

    private void InsertHelper(Page page, int data) {
        if (rootPage == null) {
            //Tree Is Empty
            rootPage = new Page(new AVL(new Node(data)));
            rootPage.initiatePage(rank, 1);
            rootPage.makeRoot();
        } else if (!page.isPageLeaf()) {
            //Current page is Not Leaf
            if (data < page.getMinValue())
                InsertHelper(page.getLeftPage(), data);
            else if (data > page.getMaxValue())
                InsertHelper(page.getRightPage(), data);
            else//Insert Inside The Page's Children Beside LeftPage and RightPage
                InsertHelper(page.getContentPage(data), data);
        } else if (!page.hasMaxNodes()) {
            //There Is Space In The Current Page
            Node node = new Node(data);
            page.insert(node);
        } else {
            //There Is No Space In The Current Page
            //Insert And Fix The Page
            Node node = new Node(data);
            page.insert(node);
            FixPage(page);
        }

    }

    private void FixPage(Page page) {
        //Page is Fixed
        if (page.size() <= page.maxNodes)
            return;
        if (page.isRoot()) {
            //Page Is Root
            //Create A New Root
            Page newRootPage = new Page(new AVL(null));
            newRootPage.initiatePage(rank, 1);
            newRootPage.makeRoot();
            page.parentPage = newRootPage;
            rootPage = newRootPage;
        }
        //Insert The Middle Element In The Current Page To The Parent Page
        Node newParentNode = page.getMiddleNode();
        page.parentPage.insert(newParentNode);

        //Create The New LeftPage for The New Node
        newParentNode.leftPage = new Page(page.getBeforeMiddleNode());

        //Assign The New Parent After Splitting
        fixContainerPageParents(newParentNode.leftPage);

        //Make The Node Before The Current Node With The Same New Page
        Node BeforeNewNode = page.Elements.predecessor(newParentNode);
        if (BeforeNewNode != null)
            BeforeNewNode.rightPage = newParentNode.leftPage;

        newParentNode.leftPage.parentPage = page.parentPage;
        newParentNode.leftPage.initiatePage(rank, page.parentPage.level + 1);

        //Create The New RightPage for The New Node
        newParentNode.rightPage = new Page(page.getAfterMiddleNode());

        //Assign The New Parent After Splitting
        fixContainerPageParents(newParentNode.rightPage);

        //Make The Node After The Current Node With The Same New Page
        Node AfterNewNode = page.Elements.successor(newParentNode);
        if (AfterNewNode != null)
            AfterNewNode.leftPage = newParentNode.rightPage;

        newParentNode.rightPage.parentPage = page.parentPage;
        newParentNode.rightPage.initiatePage(rank, page.parentPage.level + 1);

        //Continue To Fix The Parent Page
        FixPage(page.parentPage);
    }

    public void Delete(Page page, int data) {
        if (rootPage == null) {
            //Tree is Empty
            System.out.println("Can't Delete Because B-Tree is Empty!");
            //End Of Deletion
        } else {
            Page containerPage = Find(page, data);
            if (containerPage == null) {
                //Element was Not Found
                System.out.println("The Element Does Not Exist!");
                //End Of Deletion
            } else {
                //Element was Found
                if (containerPage.isPageLeaf()) {
                    deleteFromLeaf(containerPage, data);
                } else {
                    deleteFromInternalPage(containerPage, data);
                }
            }
        }
    }

    private void deleteFromLeaf(Page containerPage, int data) {
        //Container Page is A Leaf Page
        if (!containerPage.hasMinNodes()) {
            //Container Page Has More Than Min Nodes
            containerPage.delete(data);
            //End Of Deletion
        } else {
            //Container Page Has Min Nodes
            //Required Fix
            if (containerPage.parentPage.getRightPage() == containerPage) {
                //The Page is Most Right Page

                Node nextLowerNode = containerPage.getLeftParentNode();

                if (!containerPage.getLeftBrotherPage().hasMinNodes()) {
                    //Left Brother Page For The Current Page has More Than Min Nodes
                    //Rotation is Required
                    //Merging is Not Required

                    //Creating the new replacement for the next lower node
                    //which is the max node in the left brother containerPage
                    Node newReplacement = new Node(containerPage.getLeftBrotherPage().getMaxValue());

                    //Connecting old new containerPage to the new replacement
                    newReplacement.leftPage = nextLowerNode.leftPage;
                    newReplacement.rightPage = nextLowerNode.rightPage;

                    //Inserting the predecessor node and replacing the next lower node
                    containerPage.parentPage.insert(newReplacement);

                    //Delete the new replacement from its old page
                    containerPage.getLeftBrotherPage().delete(newReplacement.value);

                } else {
                    //Left Brother Page For The Current Page has Min Nodes
                    if (!containerPage.parentPage.hasMinNodes()) {
                        //Parent has more than min nodes
                        //Merging is Required

                        //Mering pages
                        mergePages(containerPage, containerPage.getLeftBrotherPage());

                        //Fixing links
                        if (containerPage.getLeftParentNode() != null)
                            containerPage.getLeftParentNode().rightPage = containerPage;
                        else
                            rootPage = containerPage;
                    } else {
                        //Merging is Not Required
                        //Parent has min nodes
                        //Fixing Parent is Required
                        int fromParentToChild = 0;
                        if (containerPage.getLeftParentNode() != null)
                            fromParentToChild = containerPage.getLeftParentNode().value;
                        else if (containerPage.getRightParentNode() != null)
                            fromParentToChild = containerPage.getRightParentNode().value;
                        Node fromParentToChildNode = new Node(fromParentToChild);
                        containerPage.insert(fromParentToChildNode);
                        containerPage.delete(data);
                        Delete(containerPage.parentPage, fromParentToChild);
                        return;
                    }

                }

                //Deleting next lower node from parent containerPage
                containerPage.parentPage.delete(nextLowerNode.value);

                //Deleting the required element
                containerPage.delete(data);

                //Adding the next lower node value to the current containerPage
                containerPage.insert(new Node(nextLowerNode.value));

                //End of Deletion
            } else {
                //The Page is Not Most Right Page

                Node nextGreaterNode = containerPage.getRightParentNode();

                if (!containerPage.getRightBrotherPage().hasMinNodes()) {
                    //Left Brother Page For The Current Page has More Than Min Nodes
                    //Rotation is Required
                    //Merging is Not Required

                    //Creating the new replacement for the next lower node
                    //which is the max node in the left brother containerPage
                    Node newReplacement = new Node(containerPage.getRightBrotherPage().getMinValue());

                    //Connecting old new containerPage to the new replacement
                    newReplacement.leftPage = nextGreaterNode.leftPage;
                    newReplacement.rightPage = nextGreaterNode.rightPage;

                    //Inserting the predecessor node and replacing the next lower node
                    containerPage.parentPage.insert(newReplacement);

                    //Delete the new replacement from its old page
                    containerPage.getRightBrotherPage().delete(newReplacement.value);

                } else {
                    //Left Brother Page For The Current Page has Min Nodes
                    if (!containerPage.parentPage.hasMinNodes()) {
                        //Parent has more than min nodes
                        //Merging is Required
                        //Mering Pages
                        mergePages(containerPage, containerPage.getRightBrotherPage());

                        //Fixing links
                        if (containerPage.getRightParentNode() != null)
                            containerPage.getRightParentNode().leftPage = containerPage;
                        if (containerPage.getLeftParentNode() != null)
                            containerPage.getLeftParentNode().rightPage = containerPage;
                        if (containerPage.getRightParentNode() == null && containerPage.getLeftParentNode() == null)
                            rootPage = containerPage;
                    } else {
                        //Merging is Not Required
                        //Parent has min nodes
                        //Fixing Parent is Required
                        int fromParentToChild = 0;
                        if (containerPage.getLeftParentNode() != null)
                            fromParentToChild = containerPage.getLeftParentNode().value;
                        else if (containerPage.getRightParentNode() != null)
                            fromParentToChild = containerPage.getRightParentNode().value;
                        Node fromParentToChildNode = new Node(fromParentToChild);
                        containerPage.insert(fromParentToChildNode);
                        containerPage.delete(data);
                        Delete(containerPage.parentPage, fromParentToChild);
                        return;
                    }
                }

                //Deleting next Greater node (Parent Node) from parent containerPage
                containerPage.parentPage.delete(nextGreaterNode.value);

                //Deleting the required element
                containerPage.delete(data);

                //Adding the next Greater node (Parent Node) value to the current containerPage
                containerPage.insert(new Node(nextGreaterNode.value));

                //End of Deletion
            }
        }
    }

    private void deleteFromInternalPage(Page containerPage, int data) {
        Node deletedNode = containerPage.find(data);
        if (!deletedNode.rightPage.hasMinNodes()) {
            //Right Page has more than min node
            //Get the successor

            //Delete the replacement value from its page
            int replacementValue = deletedNode.rightPage.getMinValue();
            Delete(deletedNode.rightPage, replacementValue);

            //Fixing Links
            Node replacementNode = new Node(replacementValue);
            replacementNode.rightPage = deletedNode.rightPage;
            replacementNode.leftPage = deletedNode.leftPage;

            //Delete the wanted data
            containerPage.delete(data);

            //Insert the new replacement
            containerPage.insert(replacementNode);

        } else if (!deletedNode.leftPage.hasMinNodes()) {
            //Left Page has more than min node
            //Get the successor

            //Delete the replacement value from its page
            int replacementValue = deletedNode.leftPage.getMaxValue();
            Delete(deletedNode.leftPage, replacementValue);

            //Fixing Links
            Node replacementNode = new Node(replacementValue);
            replacementNode.rightPage = deletedNode.rightPage;
            replacementNode.leftPage = deletedNode.leftPage;

            //Delete the wanted data
            containerPage.delete(data);

            //Insert the new replacement
            containerPage.insert(replacementNode);

        } else {
            if (!containerPage.hasMinNodes()) {
                //Left page and right page has min node
                //Container page node has more than its min nodes
                //Merging is Required

                mergeAndFix(containerPage, deletedNode);

                //Deleting Required Element
                containerPage.delete(deletedNode.value);

            } else if (containerPage == rootPage) {
                //Left page and right page has min node
                //Merging is Required
                //Fixing Root is required

                //Getting merged page
                Page mergedPage = mergePages(deletedNode.leftPage, deletedNode.rightPage);

                mergedPage.makeRoot();
                rootPage = mergedPage;

            } else {
                boolean caseThree = false;
                for (Page p : containerPage.getChildren()) {
                    if (!p.hasMinNodes()) {
                        if (p.getMinValue() > deletedNode.value) {
                            //Left Rotations
                            leftRotation(p, deletedNode.value);
                        } else {
                            //Right Rotation
                            rightRotation(p, deletedNode.value);
                        }
                        caseThree = true;
                        break;
                    }
                }
                if (!caseThree) {
                    int fromFatherNodeValue = 0;
                    if (containerPage.getLeftParentNode() != null)
                        fromFatherNodeValue = containerPage.getLeftParentNode().value;
                    else if (containerPage.getRightParentNode() != null)
                        fromFatherNodeValue = containerPage.getRightParentNode().value;
                    Node fromFatherNode = new Node(fromFatherNodeValue);
                    if (fromFatherNodeValue > containerPage.getMaxValue()) {
                        containerPage.getRightPage().insert(fromFatherNode);
                    } else {
                        containerPage.getLeftPage().insert(fromFatherNode);
                    }
                    Delete(containerPage, deletedNode.value);
                    Delete(containerPage.parentPage, fromFatherNodeValue);
                }
            }
        }
    }

    private void leftRotation(Page page, int deletedValue) {

        while (true) {
            Node leftParentNode = page.getLeftParentNode();

            Node rightToParentNode = new Node(page.getMinValue());

            rightToParentNode.leftPage = leftParentNode.leftPage;
            rightToParentNode.rightPage = leftParentNode.rightPage;

            page.delete(rightToParentNode.value);

            page.parentPage.delete(leftParentNode.value);

            page.parentPage.insert(rightToParentNode);

            if (leftParentNode.value == deletedValue) {
                return;
            }
            Node parentToLeftNode = new Node(leftParentNode.value);

            page.getLeftBrotherPage().insert(parentToLeftNode);

            page = page.getLeftBrotherPage();

        }
    }

    private void rightRotation(Page page, int deletedValue) {
        while (true) {
            Node rightParentNode = page.getRightParentNode();

            Node leftToPrentNode = new Node(page.getMaxValue());

            leftToPrentNode.leftPage = rightParentNode.leftPage;
            leftToPrentNode.rightPage = rightParentNode.rightPage;

            page.delete(leftToPrentNode.value);

            page.parentPage.delete(rightParentNode.value);

            page.parentPage.insert(leftToPrentNode);

            if (rightParentNode.value == deletedValue) {
                return;
            }
            Node parentToRightNode = new Node(rightParentNode.value);

            page.getRightBrotherPage().insert(parentToRightNode);

            page = page.getRightBrotherPage();

        }
    }

    private Page mergePages(Page left, Page right) {
        if (!left.isPageLeaf() && !right.isPageLeaf()) {
            mergePages(left.Elements.max(left.Elements.root).rightPage, right.Elements.min(right.Elements.root).leftPage);
        }

        ArrayList<Node> rightPageNodes = new ArrayList<>();
        //Getting right Brother Page Nodes
        right.getInorderNodesMatrix(rightPageNodes, right.Elements.root);
        //Inserting right Brother page nodes in left page
        for (Node node : rightPageNodes) {
            left.insert(node);
        }
        return left;
    }

    private void mergeAndFix(Page containerPage, Node deletedNode) {
        //Getting merged page
        Page mergedPage = mergePages(deletedNode.leftPage, deletedNode.rightPage);
        //Fixing links
        mergedPage.parentPage = containerPage;
        if (containerPage.Elements.predecessor(deletedNode) != null)
            containerPage.Elements.predecessor(deletedNode).rightPage = mergedPage;
        if (containerPage.Elements.successor(deletedNode) != null)
            containerPage.Elements.successor(deletedNode).leftPage = mergedPage;
        fixContainerPageParents(mergedPage);
    }

    public void printBTree(Page page) {
        if (page == null) {
            System.out.println("NULL");
            return;
        }
        Queue<ArrayList<Page>> queue = new LinkedList<>();
        ArrayList<Page> temp = new ArrayList<>();
        temp.add(page);
        queue.add(temp);
        int level = 1;
        while (!queue.isEmpty()) {
            ArrayList<Page> containerPage = queue.poll();
            System.out.print("Level " + level + " : ");
            printContainerPage(containerPage);
            System.out.println();
            ArrayList<Page> PageLevel = new ArrayList<>();
            for (Page value : containerPage) {
                if (value != null && !value.isPageLeaf()) {
                    PageLevel.addAll(value.getChildren());
                    PageLevel.add(null);
                }
            }
            if (!PageLevel.isEmpty()) {
                queue.add(PageLevel);
            }
            level++;
        }
    }

    private void printContainerPage(ArrayList<Page> containerPage) {
        for (int i = 0; i < containerPage.size(); i++) {
            if (containerPage.get(i) != null) {
                containerPage.get(i).printPage();
            }
            if (i < containerPage.size() - 1 &&
                    containerPage.get(i) != null &&
                    containerPage.get(i + 1) != null &&
                    containerPage.get(i).parentPage == containerPage.get(i + 1).parentPage)
                System.out.print("-");
            else
                System.out.print(" ");
        }
    }

    private void fixContainerPageParents(Page page) {
        Node root = page.Elements.root;
        fixContainerPageParentsHelper(page, root);
    }

    private void fixContainerPageParentsHelper(Page page, Node node) {
        if (node != null) {
            if (node.leftPage != null)
                node.leftPage.parentPage = page;
            if (node.rightPage != null)
                node.rightPage.parentPage = page;
            fixContainerPageParentsHelper(page, node.left);
            fixContainerPageParentsHelper(page, node.right);
        }
    }

    public Page getRootPage() {
        return rootPage;
    }

    public static void handleInputChoices() {
        Scanner in = new Scanner(System.in);
        int choice;
        boolean input = false;
        BTree btree = null;
        while (!input) {
            System.out.print("Enter : "
                    + "\n 1- Auto Generate Tree"
                    + "\n 2- Enter Elements Manually"
                    + "\nCommand :");
            choice = in.nextInt();
            switch (choice) {
                case 1: {
                    System.out.print("Enter Tree Rank : ");
                    int rank = in.nextInt();
                    btree = new BTree(rank);
                    System.out.print("Enter Number Of Elements : ");
                    int n = in.nextInt();
                    //boolean flag=true;
                    for (int i = 1; i <= 2 * n; i += 2) {
                        btree.Insert(btree.getRootPage(), i);
                        /*if(flag) {
                            btree.Insert(btree.getRootPage(), i);
                            flag=!flag;
                        }else{
                            btree.Insert(btree.getRootPage(), i*2);
                            flag=!flag;
                        }*/
                    }
                    btree.printBTree(btree.getRootPage());
                    input = true;
                    break;
                }
                case 2: {
                    System.out.print("Enter Tree Rank : ");
                    int rank = in.nextInt();
                    btree = new BTree(rank);
                    System.out.print("Enter Number Of Elements : ");
                    int n = in.nextInt();
                    System.out.print("Enter Elements : ");
                    for (int i = 1; i <= 2 * n; i += 2) {
                        int element = in.nextInt();
                        btree.Insert(btree.getRootPage(), element);
                    }
                    btree.printBTree(btree.getRootPage());
                    input = true;
                    break;
                }
                default: {
                    break;
                }
            }

        }
        input = false;
        while (!input) {
            System.out.print("Enter : "
                    + "\n 1- Add Element"
                    + "\n 2- Delete Element"
                    + "\n 3- Search For An Element"
                    + "\n 4- Find Element Page"
                    + "\n-1- To Back"
                    + "\n 0- To Exit"
                    + "\nCommand :");
            choice = in.nextInt();
            switch (choice) {
                case 1: {
                    System.out.print("Enter Element You Want To Add : ");
                    int element = in.nextInt();
                    btree.Insert(btree.getRootPage(), element);
                    btree.printBTree(btree.getRootPage());
                    break;
                }
                case 2: {
                    System.out.print("Enter Element You Want To Delete : ");
                    int element = in.nextInt();
                    btree.Delete(btree.getRootPage(), element);
                    btree.printBTree(btree.getRootPage());
                    break;
                }
                case 3: {
                    System.out.print("Enter Element : ");
                    int element = in.nextInt();
                    if (btree.Search(btree.getRootPage(), element)) {
                        System.out.println("The Element Exist!");
                    } else {
                        System.out.println("The Element Does Not Exist!");
                    }
                    btree.printBTree(btree.getRootPage());
                    break;
                }
                case 4: {
                    System.out.print("Enter Element : ");
                    int element = in.nextInt();
                    Page page = btree.Find(btree.getRootPage(), element);
                    if (page != null) {
                        System.out.println("The Element Exist!");
                        System.out.print("The Container Page : ");
                        page.printPage();
                        System.out.println();
                        if (page.parentPage != null) {
                            System.out.print("Parent Page : ");
                            page.parentPage.printPage();
                            System.out.println();
                        }
                    } else {
                        System.out.println("The Element Does Not Exist!");
                    }
                    btree.printBTree(btree.getRootPage());
                    break;
                }
                case -1: {
                    input = true;
                    break;
                }
                case 0: {
                    System.exit(0);
                }
                default: {
                    break;
                }
            }

        }
    }
}
