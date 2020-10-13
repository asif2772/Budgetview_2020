package vb
/**
 * Created by Animesh on 4/18/18.
 */
public class AhoCorasick {
    static final int ALPHABET_SIZE = 255

    Node[] nodes
    int nodeCount

    public static class Node {
        int parent
        char charFromParent
        int suffLink = -1
        int[] children = new int[ALPHABET_SIZE]
        int[] transitions = new int[ALPHABET_SIZE]
        boolean leaf
        int dictId
        {
            Arrays.fill(children, -1)
            Arrays.fill(transitions, -1)
        }
    }

    public AhoCorasick(int maxNodes) {
        nodes = new Node[maxNodes]
        // create root
        nodes[0] = new Node()
        nodes[0].suffLink = 0
        nodes[0].parent = -1
        nodeCount = 1
    }

    public void addString(String s, int dictId) {
        int cur = 0
        for (char ch : s.toCharArray()) {
            int c = ch
            if (nodes[cur].children[c] == -1) {
                nodes[nodeCount] = new Node()
                nodes[nodeCount].parent = cur
                nodes[nodeCount].charFromParent = ch
                nodes[cur].children[c] = nodeCount++
            }
            cur = nodes[cur].children[c]
        }
        nodes[cur].leaf = true
        nodes[cur].dictId = dictId
    }

    public int suffLink(int nodeIndex) {
        Node node = nodes[nodeIndex]
        if (node.suffLink == -1)
            node.suffLink = node.parent == 0 ? 0 : transition(suffLink(node.parent), node.charFromParent);
        return node.suffLink
    }

    public int transition(int nodeIndex, char ch) {
        int c = ch
        Node node = nodes[nodeIndex]
        if (node.transitions[c] == -1)
            node.transitions[c] = node.children[c] != -1 ? node.children[c] : (nodeIndex == 0 ? 0 : transition(suffLink(nodeIndex), ch))
        return node.transitions[c]
    }
}


