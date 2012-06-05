package olivsoftdesktop.param;

import java.awt.BorderLayout;

import javax.swing.event.TreeSelectionEvent;
import javax.swing.event.TreeSelectionListener;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.JTree;
import javax.swing.ToolTipManager;
import javax.swing.tree.DefaultTreeModel;
import javax.swing.tree.TreePath;

import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeCellRenderer;
import javax.swing.tree.TreeNode;

public class CategoryJTreeHolder 
    extends JPanel
{
  private BorderLayout borderLayout1 = new BorderLayout();
  private JScrollPane jScrollPane = new JScrollPane();

  private JTree dataTree = new JTree();
  private final TreeSelectionListener treeMonitor = new TreeMonitor();

  private DefaultMutableTreeNode root = null;
  TreeNode currentlySelectedNode = null;

  CategoryPanel parent;
  
  public CategoryJTreeHolder(CategoryPanel pp)
  {
    parent = pp;
    try
    {
      jbInit();
    }
    catch (Exception e)
    {
      e.printStackTrace();
    }
  }
  
  private void jbInit()
    throws Exception
  {
    this.setLayout(borderLayout1);
    jScrollPane.getViewport().add(dataTree, null);
    this.add(jScrollPane, BorderLayout.CENTER);
    dataTree.addTreeSelectionListener(treeMonitor);
    // Enable Tooltips
    ToolTipManager.sharedInstance().registerComponent(dataTree);
    root = new DefaultMutableTreeNode("invisible-root");
    if (root != null)
    {
      dataTree.setModel(new DefaultTreeModel((TreeNode)root)); 
      DefaultTreeCellRenderer renderer = (DefaultTreeCellRenderer)dataTree.getCellRenderer();
      // Remove the icons
      renderer.setLeafIcon(null);
      renderer.setClosedIcon(null);
      renderer.setOpenIcon(null);
    }
    dataTree.setRootVisible(false);
    
    for (int i=0; i<CategoryPanel.CATEGORIES.length; i++)
      root.add(new DefaultMutableTreeNode(CategoryPanel.CATEGORIES[i], true));

    ((DefaultTreeModel)dataTree.getModel()).reload(root);
  }
  
  class TreeMonitor implements TreeSelectionListener
  {
    JTextField feedback = null;
    
    public TreeMonitor()
    {
      this(null);
    }

    public TreeMonitor(JTextField fld)
    {
      feedback = fld;
    }
    
    public void valueChanged(TreeSelectionEvent tse)
    {
      TreePath tp = (TreePath)tse.getNewLeadSelectionPath();
      if (tp == null)
        return;
      DefaultMutableTreeNode dtn = (DefaultMutableTreeNode)tp.getLastPathComponent();      
      currentlySelectedNode = dtn;
//    System.out.println("Selected " + dtn.getUserObject().toString());
      parent.getEventFormTree(dtn.getUserObject().toString());
    }
  }
}