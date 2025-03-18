class TextState {
    String content;
    TextState next;
    TextState prev;

    public TextState(String content) {
        this.content = content;
        this.next = null;
        this.prev = null;
    }
}

public class TextEditor {
    private TextState head, tail, current;

    public TextEditor() {
        this.head = this.tail = this.current = null;
    }

    // Add a new text state at the end (recording a new action)
    public void addTextState(String newText) {
        TextState newState = new TextState(newText);

        // If there is no history, set it as the first state
        if (head == null) {
            head = tail = current = newState;
        } else {
            // Remove forward history (if any redo states exist)
            current.next = null;
            newState.prev = current;
            current.next = newState;
            current = newState;
            tail = newState; // Update tail
        }
    }

    // Undo functionality (revert to previous state)
    public void undo() {
        if (current != null && current.prev != null) {
            current = current.prev;
        } else {
            System.out.println("No more undo actions available.");
        }
    }

    // Redo functionality (revert to next state)
    public void redo() {
        if (current != null && current.next != null) {
            current = current.next;
        } else {
            System.out.println("No more redo actions available.");
        }
    }

    // Display the current state of the text
    public void displayCurrentState() {
        if (current != null) {
            System.out.println("Current State: " + current.content);
        } else {
            System.out.println("No text recorded.");
        }
    }

    public static void main(String[] args) {
        TextEditor editor = new TextEditor();

        // Simulating text changes
        editor.addTextState("Hello");
        editor.addTextState("Hello,I am Pranav.");
        editor.addTextState("Hello!How are you?");
        editor.displayCurrentState();

        // Undo actions
        editor.undo();
        editor.displayCurrentState(); // Expected:
        editor.undo();
        editor.displayCurrentState(); // Expected:

        // Redo actions
        editor.redo();
        editor.displayCurrentState();
        editor.redo();
        editor.displayCurrentState();

        // Adding a new state after undo (clears redo history)
        editor.addTextState("Pranav! How are you today?");
        editor.displayCurrentState();

        // Trying to redo (should not be possible)
        editor.redo(); // Expected: "No more redo actions available."
    }
}
