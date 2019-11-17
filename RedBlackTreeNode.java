public class RedBlackTreeNode<K, V> {
    K key;
    V value;
    NodeColor nodeColor;

    RedBlackTreeNode<K, V> parent;
    RedBlackTreeNode<K, V> left;
    RedBlackTreeNode<K, V> right;

    RedBlackTreeNode(K key, V value) {
        this.key = key;
        this.value = value;
        this.nodeColor = NodeColor.RED;
    }

    public NodeColor getNodeColor() {
        return nodeColor;
    }

    public void setNodeColor(NodeColor nodeColor) {
        this.nodeColor = nodeColor;
    }
}

enum NodeColor {
    RED, BLACK
}