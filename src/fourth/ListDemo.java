package fourth;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.event.*;

public class ListDemo extends JPanel
		implements ListSelectionListener {
	private JList list;
	private DefaultListModel listModel;
	private static final String add = "添加";
	private static final String remove = "删除";
	private JButton removeButton;
	private JTextField jtField;

	public ListDemo() {
		super(new BorderLayout());

		listModel = new DefaultListModel();
		listModel.addElement("Jane Doe");
		listModel.addElement("John Smith");
		listModel.addElement("Kathy Green");

		//Create the list and put it in a scroll pane.
		list = new JList(listModel);
		list.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		list.setSelectedIndex(0);
		list.addListSelectionListener(this);
		list.setVisibleRowCount(5);
		JScrollPane listScrollPane = new JScrollPane(list);

		JButton hireButton = new JButton(add);
		HireListener hireListener = new HireListener(hireButton);
		hireButton.setActionCommand(add);
		hireButton.addActionListener(hireListener);
		hireButton.setEnabled(false);

		removeButton = new JButton(remove);
		removeButton.setActionCommand(remove);
		removeButton.addActionListener(new FireListener());

		jtField = new JTextField(10);
		jtField.addActionListener(hireListener);
		jtField.getDocument().addDocumentListener(hireListener);
		//String name = listModel.getElementAt(list.getSelectedIndex()).toString();


		JPanel buttonPane = new JPanel();
		buttonPane.setLayout(new BoxLayout(buttonPane,BoxLayout.LINE_AXIS));
		buttonPane.add(removeButton);
		buttonPane.add(Box.createHorizontalStrut(5));
		buttonPane.add(new JSeparator(SwingConstants.VERTICAL));
		buttonPane.add(Box.createHorizontalStrut(5));
		buttonPane.add(jtField);
		buttonPane.add(hireButton);
		buttonPane.setBorder(BorderFactory.createEmptyBorder(5,5,5,5));

		add(listScrollPane, BorderLayout.CENTER);
		add(buttonPane, BorderLayout.SOUTH);
	}

	class FireListener implements ActionListener {
		public void actionPerformed(ActionEvent e) {
			//This method can be called only if
			//there's a valid selection
			//so go ahead and remove whatever's selected.
			int index = list.getSelectedIndex();
			listModel.remove(index);

			int size = listModel.getSize();

			if (size == 0) { //Nobody's left, disable firing.
				removeButton.setEnabled(false);

			} else { //Select an index.
				if (index == listModel.getSize()) {
					//removed item in last position
					index--;
				}

				list.setSelectedIndex(index);
				list.ensureIndexIsVisible(index);
			}
		}
	}

	//This listener is shared by the text field and the hire button.
	class HireListener implements ActionListener, DocumentListener {
		private boolean alreadyEnabled = false;
		private JButton button;

		public HireListener(JButton button) {
			this.button = button;
		}

		//Required by ActionListener.
		public void actionPerformed(ActionEvent e) {
			String name = jtField.getText();

			//User didn't type in a unique name...
			if (name.equals("") || alreadyInList(name)) {
				Toolkit.getDefaultToolkit().beep();
				jtField.requestFocusInWindow();
				jtField.selectAll();
				return;
			}

			int index = list.getSelectedIndex(); //get selected index
			if (index == -1) { //no selection, so insert at beginning
				index = 0;
			} else {           //add after the selected item
				index++;
			}

			listModel.insertElementAt(jtField.getText(), index);
			//If we just wanted to add to the end, we'd do this:
			//listModel.addElement(jtField.getText());

			//Reset the text field.
			jtField.requestFocusInWindow();
			jtField.setText("");

			//Select the new item and make it visible.
			list.setSelectedIndex(index);
			list.ensureIndexIsVisible(index);
		}

		//This method tests for string equality. You could certainly
		//get more sophisticated about the algorithm.  For example,
		//you might want to ignore white space and capitalization.
		protected boolean alreadyInList(String name) {
			return listModel.contains(name);
		}

		//Required by DocumentListener.
		public void insertUpdate(DocumentEvent e) {
			enableButton();
		}

		//Required by DocumentListener.
		public void removeUpdate(DocumentEvent e) {
			handleEmptyTextField(e);
		}

		//Required by DocumentListener.
		public void changedUpdate(DocumentEvent e) {
			if (!handleEmptyTextField(e)) {
				enableButton();
			}
		}

		private void enableButton() {
			if (!alreadyEnabled) {
				button.setEnabled(true);
			}
		}

		private boolean handleEmptyTextField(DocumentEvent e) {
			if (e.getDocument().getLength() <= 0) {
				button.setEnabled(false);
				alreadyEnabled = false;
				return true;
			}
			return false;
		}
	}

	//This method is required by ListSelectionListener.
	public void valueChanged(ListSelectionEvent e) {
		if (e.getValueIsAdjusting() == false) {

			if (list.getSelectedIndex() == -1) {
				//No selection, disable fire button.
				removeButton.setEnabled(false);

			} else {
				//Selection, enable the fire button.
				removeButton.setEnabled(true);
			}
		}
	}

	/**
	 * Create the GUI and show it.  For thread safety,
	 * this method should be invoked from the
	 * event-dispatching thread.
	 */
	private static void createAndShowGUI() {
		//Create and set up the window.
		JFrame frame = new JFrame("ListDemo");
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		//Create and set up the content pane.
		JComponent newContentPane = new ListDemo();
		newContentPane.setOpaque(false); //content panes must be opaque
		frame.setContentPane(newContentPane);

		//Display the window.
		frame.pack();
		frame.setVisible(true);
	}

	public static void main(String[] args) {
		//Schedule a job for the event-dispatching thread:
		//creating and showing this application's GUI.
		javax.swing.SwingUtilities.invokeLater(new Runnable() {
			public void run() {
				createAndShowGUI();
			}
		});
	}
}