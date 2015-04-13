
public interface Node {
    Node getParent();
    Node getFirstChild();
    Node getLastChild();
    Node getNextSibling();
    Node getPreviousSibling();
    void insertChildBefore(Node newChild, Node child);
    void appendChild(Node newChild);
    void removeChild(Node child);
    void replaceChild(Node newChild, Node oldChild);
}

public interface Document extends Node {
    void write(OutputStream output) throws IOException;
}

public interface Element extends Node {
    String getTagName();
    void setTagName(String tagName);
    String getAttributeValue(String name);
    int getAttributeCount();
    String getAttributeName(int i);
    String getAttributeValue(int i);
    void setAttribute(String name, String value);
}

public interface Text extends Node {
    String getData();
    void setData(String data);
}


