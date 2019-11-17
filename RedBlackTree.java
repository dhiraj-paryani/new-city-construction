import java.util.*;

class RedBlackTree<K, V> {
    private RedBlackTreeNode<K, V> head;
    private Comparator<? super K> comparator;

    RedBlackTree(Comparator<? super K> comparator) {
        this.comparator = comparator;
    }

    boolean addElement(K key, V value) {
        RedBlackTreeNode<K, V> node = new RedBlackTreeNode<>(key, value);

        if(head == null) {
            node.setNodeColor(NodeColor.BLACK);
            head = node;
        } else {
            RedBlackTreeNode<K, V> current = head;
            RedBlackTreeNode<K, V> parent = head;
            while(current != null) {
                parent = current;
                int compareValue = comparator.compare(node.key, current.key);
                if(compareValue < 0) {
                    current = current.left;
                } else if(compareValue > 0) {
                    current = current.right;
                } else {
                    return false;
                }
            }
            node.parent = parent;
            if(comparator.compare(node.key, parent.key) < 0) {
                parent.left = node;
            } else {
                parent.right = node;
            }

            setUpTreeAfterInsert(node);
        }
        return true;
    }

    private void setUpTreeAfterInsert(RedBlackTreeNode<K, V> node) {
        RedBlackTreeNode<K, V> parent = node.parent;

        if(NodeColor.RED.equals(node.getNodeColor()) && NodeColor.RED.equals(parent.getNodeColor())) {


            RedBlackTreeNode<K, V> grandParent = node.parent.parent;
            NodeColor gpOtherChildColor;

            if(grandParent.left == parent) {
                gpOtherChildColor = grandParent.right == null ? NodeColor.BLACK : grandParent.right.nodeColor;
            } else {
                gpOtherChildColor = grandParent.left == null ? NodeColor.BLACK : grandParent.left.nodeColor;
            }

            if(NodeColor.RED.equals(gpOtherChildColor)) {
                grandParent.left.setNodeColor(NodeColor.BLACK);
                if(grandParent.right != null) grandParent.right.nodeColor = NodeColor.BLACK;
                if(grandParent != head) {
                    grandParent.setNodeColor(NodeColor.RED);
                    setUpTreeAfterInsert(grandParent);
                }
            } else {
                if(grandParent.left == parent) {
                    if(parent.left == node) {
                        LLShift(parent);
                    } else if(parent.right == node){
                        RRShift(parent.right);
                        LLShift(grandParent.left);
                    }
                } else if(grandParent.right == parent) {
                    if(parent.right == node) {
                        RRShift(parent);
                    } else if(parent.left == node) {
                        LLShift(parent.left);
                        RRShift(grandParent.right);
                    }
                }
            }
        }
    }

    private void LLShift(RedBlackTreeNode<K, V> node) {
        // System.out.println("LL Shift: " + node.key);
        RedBlackTreeNode<K, V> nodeRight = node.right;
        RedBlackTreeNode<K, V> nodeParent = node.parent;

        node.right = nodeParent;
        node.parent = nodeParent.parent;
        if(node.parent == null) {
            head = node;
        } else {
            if(node.parent.left == nodeParent) {
                node.parent.left = node;
            } else {
                node.parent.right = node;
            }
        }
        node.right.parent = node;
        node.right.left = nodeRight;
        if(nodeRight != null) nodeRight.parent = node.right;

        NodeColor temp = node.nodeColor;
        node.nodeColor = nodeParent.nodeColor;
        nodeParent.nodeColor = temp;

    }

    private void RRShift(RedBlackTreeNode<K, V> node) {
        // System.out.println("RR Shift");
        RedBlackTreeNode<K, V> nodeLeft = node.left;
        RedBlackTreeNode<K, V> nodeParent = node.parent;

        node.left = nodeParent;
        node.parent = nodeParent.parent;
        if(node.parent == null) {
            head = node;
        } else {
            if(node.parent.left == nodeParent) {
                node.parent.left = node;
            } else {
                node.parent.right = node;
            }
        }
        node.left.parent = node;
        node.left.right = nodeLeft;
        if(nodeLeft != null) nodeLeft.parent = node.left;

        NodeColor temp = node.nodeColor;
        node.nodeColor = nodeParent.nodeColor;
        nodeParent.nodeColor = temp;
    }

    void printInOrder() {
        printInOrder(head);
    }

    void printInOrder(RedBlackTreeNode<K, V> head) {
        if(head == null) {
            return;
        }
        printInOrder(head.left);
        System.out.print(head.key + ":" + head.nodeColor + " ");
        printInOrder(head.right);
    }

    boolean deleteElement(K element) {
        RedBlackTreeNode<K, V> nodeToBeDeleted = findElement(head, element);
        if (nodeToBeDeleted == null) return false;

        int degreeOfNodeToBeDeleted = findDegree(nodeToBeDeleted);

        if(degreeOfNodeToBeDeleted == 2) {
            RedBlackTreeNode<K, V> inOrderSuccessor = findInOrderSuccessor(nodeToBeDeleted);
            nodeToBeDeleted.key = inOrderSuccessor.key;
            nodeToBeDeleted = inOrderSuccessor;
        }

        RedBlackTreeNode<K, V> childOfDeletedNode = nodeToBeDeleted.left == null ? nodeToBeDeleted.right : nodeToBeDeleted.left;
        RedBlackTreeNode<K, V> parentOfDeletedNode = nodeToBeDeleted.parent;
        if(parentOfDeletedNode == null) {
            if(childOfDeletedNode == null) {
                head = null;
                return true;
            }
            childOfDeletedNode.nodeColor = NodeColor.BLACK;
            childOfDeletedNode.parent = null;
            head = childOfDeletedNode;
            return true;
        }
        if (parentOfDeletedNode.right == nodeToBeDeleted) {
            parentOfDeletedNode.right = childOfDeletedNode;
        } else {
            parentOfDeletedNode.left = childOfDeletedNode;
        }
        if(childOfDeletedNode != null) childOfDeletedNode.parent = parentOfDeletedNode;

        if(isRedNode(nodeToBeDeleted) || isRedNode(childOfDeletedNode)) {
            if(childOfDeletedNode != null) childOfDeletedNode.nodeColor = NodeColor.BLACK;
        } else {
            printInOrder(head);
            reBalance(childOfDeletedNode, parentOfDeletedNode);
        }
        return true;
    }

    private void reBalance(RedBlackTreeNode<K, V> doubleBlackNode, RedBlackTreeNode<K, V> doubleBlackNodeParent) {

        // Case-1
        if(doubleBlackNode == head) {
            // System.out.println("case-1");
            return;
        }

        RedBlackTreeNode<K, V> doubleBlackNodeSibling = doubleBlackNodeParent.left == doubleBlackNode ? doubleBlackNodeParent.right : doubleBlackNodeParent.left;
        RedBlackTreeNode<K, V> doubleBlackNodeSiblingLeft = doubleBlackNodeSibling.left;
        RedBlackTreeNode<K, V> doubleBlackNodeSiblingRight = doubleBlackNodeSibling.right;

        System.out.println(doubleBlackNodeSibling.key);

        // Case-2
        if(isRedNode(doubleBlackNodeSibling)) {
            if(doubleBlackNodeParent.left == doubleBlackNode) {
                // System.out.println("case-2 L");
                RRShift(doubleBlackNodeSibling);
            } else if(doubleBlackNodeParent.right == doubleBlackNode) {
                // System.out.println("case-2 R");
                LLShift(doubleBlackNodeSibling);
            }
            doubleBlackNodeSibling.nodeColor = NodeColor.BLACK;
            doubleBlackNodeParent.nodeColor = NodeColor.RED;
            reBalance(doubleBlackNodeSibling, doubleBlackNodeSibling.parent);
            return;
        }

        // Case-3
        if(isBlackNode(doubleBlackNodeParent) && isBlackNode(doubleBlackNodeSibling) &&
                isBlackNode(doubleBlackNodeSiblingLeft) && isBlackNode(doubleBlackNodeSiblingRight)) {
            // System.out.println("Case-3");
            doubleBlackNodeSibling.nodeColor = NodeColor.RED;
            reBalance(doubleBlackNodeParent, doubleBlackNodeParent.parent);
            return;
        }

        // Case-4
        if(isRedNode(doubleBlackNodeParent) && isBlackNode(doubleBlackNodeSibling) &&
                isBlackNode(doubleBlackNodeSiblingLeft) && isBlackNode(doubleBlackNodeSiblingRight)) {
            // System.out.println("Case-4: " + doubleBlackNodeSibling.key);
            doubleBlackNodeSibling.nodeColor = NodeColor.RED;
            doubleBlackNodeParent.nodeColor = NodeColor.BLACK;
            return;
        }

        // Case-5
        if(isBlackNode(doubleBlackNodeParent)) {
            if(doubleBlackNodeParent.left == doubleBlackNode && isRedNode(doubleBlackNodeSiblingRight) && isBlackNode(doubleBlackNodeSiblingLeft)) {
                // System.out.println("Case-5 L");
                LLShift(doubleBlackNodeSiblingLeft);
                reBalance(doubleBlackNode, doubleBlackNodeParent);
                return;
            } else if(doubleBlackNodeParent.right == doubleBlackNode && isRedNode(doubleBlackNodeSiblingLeft) && isBlackNode(doubleBlackNodeSiblingRight)) {
                // System.out.println("Case-5 R");
                RRShift(doubleBlackNodeSiblingRight);
                reBalance(doubleBlackNode, doubleBlackNodeParent);
                return;
            }
        }

        // Case-6
        if(doubleBlackNodeParent.left == doubleBlackNode && isBlackNode(doubleBlackNodeSibling) && isRedNode(doubleBlackNodeSiblingRight)) {
            // System.out.println("Case-6 L");
            RRShift(doubleBlackNodeSibling);
            doubleBlackNodeSiblingRight.nodeColor = NodeColor.BLACK;
        } else if(doubleBlackNodeParent.right == doubleBlackNode && isBlackNode(doubleBlackNodeSibling) && isRedNode(doubleBlackNodeSiblingLeft)) {
            // System.out.println("Case-6 R");
            LLShift(doubleBlackNodeSibling);
            doubleBlackNodeSiblingLeft.nodeColor = NodeColor.BLACK;
        }

    }

    V searchElement(K key) {
        return findElement(head, key).value;
    }

    List<V> getElementsBetweenRange(K key1, K key2) {
        List<V> elements = new ArrayList<>();
        getElementsBetweenRange(head, key1, key2, elements);
        return elements;
    }

    private void getElementsBetweenRange(RedBlackTreeNode<K, V> head, K key1, K key2, List<V> elements) {
        if(head == null) {
            return;
        }
        if(comparator.compare(head.key, key1) >= 0 && comparator.compare(head.key, key2) <= 0) {
            getElementsBetweenRange(head.left, key1, key2, elements);
            elements.add(head.value);
            getElementsBetweenRange(head.right, key1, key2, elements);
            return;
        }
        if(comparator.compare(head.key, key1) >= 0) {
            getElementsBetweenRange(head.right, key1, key2, elements);
            return;
        }
        if(comparator.compare(head.key, key2) <= 0) {
            getElementsBetweenRange(head.left, key1, key2, elements);
        }
    }
    private RedBlackTreeNode<K, V> findElement(RedBlackTreeNode<K, V> head, K element) {
        if(head == null || element == null) {
            return null;
        }
        int diff = comparator.compare(head.key, element);
        if(diff == 0) {
            return head;
        } else if(diff < 0) {
            return findElement(head.right, element);
        } else {
            return findElement(head.left, element);
        }
    }

    private int findDegree(RedBlackTreeNode<K, V> node) {
        int degree = 0;
        if(node.right != null) {
            degree++;
        }
        if(node.left != null) {
            degree++;
        }
        return degree;
    }

    private RedBlackTreeNode<K, V> findInOrderSuccessor(RedBlackTreeNode<K, V> node) {
        node = node.right;
        RedBlackTreeNode<K, V> parent = node;
        while(node != null) {
            parent = node;
            node = node.left;
        }
        return parent;
    }

    private boolean isRedNode(RedBlackTreeNode<K, V> node) {
        return node != null && NodeColor.RED.equals(node.nodeColor);
    }

    private boolean isBlackNode(RedBlackTreeNode<K, V> node) {
        return node == null || NodeColor.BLACK.equals(node.nodeColor);
    }
}
