public class RedBlackTreeNode<K, V> {
    public K key;
    public V value;
    public NodeColor nodeColor;

    public RedBlackTreeNode<K, V> parent;
    public RedBlackTreeNode<K, V> left;
    public RedBlackTreeNode<K, V> right;

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