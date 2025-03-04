package DSA;

import javax.swing.*;
import java.awt.*;
import java.util.*;

class Node {
    String name;
    int id;
    
    public Node(String name, int id) {
        this.name = name;
        this.id = id;
    }
}

class Edge implements Comparable<Edge> {
    Node source, destination;
    int cost, bandwidth;
    
    public Edge(Node source, Node destination, int cost, int bandwidth) {
        this.source = source;
        this.destination = destination;
        this.cost = cost;
        this.bandwidth = bandwidth;
    }
    
    @Override
    public int compareTo(Edge other) {
        return this.cost - other.cost;
    }
}

public class NetworkOptimizer extends JFrame {
    private JPanel graphPanel;
    private JButton addNodeButton, addEdgeButton, findMSTButton, shortestPathButton;
    private JTextArea outputArea;
    private ArrayList<Node> nodes;
    private ArrayList<Edge> edges;
    private int nodeIdCounter = 0;

    public NetworkOptimizer() {
        nodes = new ArrayList<>();
        edges = new ArrayList<>();
        
        setTitle("Network Optimizer");
        setSize(800, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());
        
        graphPanel = new JPanel();
        add(graphPanel, BorderLayout.CENTER);
        
        JPanel controlPanel = new JPanel();
        addNodeButton = new JButton("Add Node");
        addEdgeButton = new JButton("Add Edge");
        findMSTButton = new JButton("Find MST");
        shortestPathButton = new JButton("Find Shortest Path");
        outputArea = new JTextArea(5, 50);
        
        controlPanel.add(addNodeButton);
        controlPanel.add(addEdgeButton);
        controlPanel.add(findMSTButton);
        controlPanel.add(shortestPathButton);
        add(controlPanel, BorderLayout.NORTH);
        add(new JScrollPane(outputArea), BorderLayout.SOUTH);
        
        addNodeButton.addActionListener(e -> addNode());
        addEdgeButton.addActionListener(e -> addEdge());
        findMSTButton.addActionListener(e -> findMST());
        shortestPathButton.addActionListener(e -> findShortestPath());
    }
    
    private void addNode() {
        String nodeName = JOptionPane.showInputDialog("Enter node name:");
        if (nodeName != null && !nodeName.trim().isEmpty()) {
            nodes.add(new Node(nodeName, nodeIdCounter++));
            outputArea.append("Added node: " + nodeName + "\n");
        }
    }
    
    private void addEdge() {
        if (nodes.size() < 2) {
            JOptionPane.showMessageDialog(this, "At least two nodes required to add an edge.");
            return;
        }
        
        String sourceName = JOptionPane.showInputDialog("Enter source node:");
        String destName = JOptionPane.showInputDialog("Enter destination node:");
        int cost = Integer.parseInt(JOptionPane.showInputDialog("Enter connection cost:"));
        int bandwidth = Integer.parseInt(JOptionPane.showInputDialog("Enter bandwidth:"));
        
        Node source = null, destination = null;
        for (Node node : nodes) {
            if (node.name.equals(sourceName)) source = node;
            if (node.name.equals(destName)) destination = node;
        }
        
        if (source != null && destination != null) {
            edges.add(new Edge(source, destination, cost, bandwidth));
            outputArea.append("Added edge: " + sourceName + " - " + destName + " (Cost: " + cost + ", Bandwidth: " + bandwidth + ")\n");
        } else {
            JOptionPane.showMessageDialog(this, "Invalid node names.");
        }
    }
    
    private void findMST() {
        if (edges.size() < nodes.size() - 1) {
            JOptionPane.showMessageDialog(this, "Not enough edges to form a spanning tree.");
            return;
        }
        
        Collections.sort(edges);
        
        HashMap<Node, Node> parent = new HashMap<>();
        for (Node node : nodes) {
            parent.put(node, node);
        }
        
        int totalCost = 0;
        outputArea.append("\nMST Edges:\n");
        
        for (Edge edge : edges) {
            Node root1 = find(parent, edge.source);
            Node root2 = find(parent, edge.destination);
            
            if (root1 != root2) {
                parent.put(root1, root2);
                totalCost += edge.cost;
                outputArea.append(edge.source.name + " - " + edge.destination.name + " (Cost: " + edge.cost + ")\n");
            }
        }
        outputArea.append("Total MST Cost: " + totalCost + "\n");
    }
    
    private Node find(HashMap<Node, Node> parent, Node node) {
        if (parent.get(node) != node)
            parent.put(node, find(parent, parent.get(node)));
        return parent.get(node);
    }
    
    private void findShortestPath() {
        String startName = JOptionPane.showInputDialog("Enter start node:");
        String endName = JOptionPane.showInputDialog("Enter end node:");
        
        Node start = null, end = null;
        for (Node node : nodes) {
            if (node.name.equals(startName)) start = node;
            if (node.name.equals(endName)) end = node;
        }
        
        if (start == null || end == null) {
            JOptionPane.showMessageDialog(this, "Invalid node names.");
            return;
        }
        
        HashMap<Node, Integer> distances = new HashMap<>();
        PriorityQueue<Edge> pq = new PriorityQueue<>(Comparator.comparingInt(e -> e.cost));
        
        for (Node node : nodes) distances.put(node, Integer.MAX_VALUE);
        distances.put(start, 0);
        
        for (Edge edge : edges) {
            if (edge.source == start) pq.add(edge);
        }
        
        while (!pq.isEmpty()) {
            Edge edge = pq.poll();
            if (distances.get(edge.destination) > distances.get(edge.source) + edge.cost) {
                distances.put(edge.destination, distances.get(edge.source) + edge.cost);
                pq.add(edge);
            }
        }
        
        outputArea.append("Shortest Path Cost from " + startName + " to " + endName + " is " + distances.get(end) + "\n");
    }
    
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new NetworkOptimizer().setVisible(true));
    }
}
