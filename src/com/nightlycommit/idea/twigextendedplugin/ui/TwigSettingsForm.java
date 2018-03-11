package com.nightlycommit.idea.twigextendedplugin.ui;

import com.intellij.openapi.fileChooser.FileChooser;
import com.intellij.openapi.fileChooser.FileChooserDescriptor;
import com.intellij.openapi.fileChooser.FileChooserDescriptorFactory;
import com.intellij.openapi.options.Configurable;
import com.intellij.openapi.options.ConfigurationException;
import com.intellij.openapi.project.Project;
import com.intellij.openapi.ui.TextFieldWithBrowseButton;
import com.intellij.openapi.vfs.VfsUtil;
import com.intellij.openapi.vfs.VirtualFile;
import com.intellij.ui.ToolbarDecorator;
import com.intellij.ui.table.TableView;
import com.intellij.util.ui.ColumnInfo;
import com.intellij.util.ui.ElementProducer;
import com.intellij.util.ui.ListTableModel;
import com.nightlycommit.idea.twigextendedplugin.Settings;
import com.nightlycommit.idea.twigextendedplugin.Settings;
import com.nightlycommit.idea.twigextendedplugin.templating.path.JsonFileIndexTwigNamespaces;
import com.nightlycommit.idea.twigextendedplugin.templating.path.TwigNamespaceSetting;
import com.nightlycommit.idea.twigextendedplugin.templating.path.TwigPath;
import com.nightlycommit.idea.twigextendedplugin.templating.util.TwigUtil;
import org.jetbrains.annotations.Nls;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import javax.swing.*;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.event.TableModelEvent;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * @author Daniel Espendiller <daniel@espendiller.net>
 */
public class TwigSettingsForm implements Configurable {

    private TextFieldWithBrowseButton manifestPath;
    private JPanel panel1;
    private JPanel panelTableView;
    private JButton buttonJsonExample;
    private TableView<TwigPath> tableView;
    private Project project;
    private boolean changed = false;
    private ListTableModel<TwigPath> modelList;

    public TwigSettingsForm(@NotNull Project project) {

        this.project = project;

        String s = getSettings().namespacesManifestPath;

        manifestPath.setText(getSettings().namespacesManifestPath);
        manifestPath.getButton().addMouseListener(createPathButtonMouseListener(manifestPath.getTextField(), FileChooserDescriptorFactory.createSingleFileDescriptor("json")));
        manifestPath.getTextField().getDocument().addDocumentListener(new DocumentListener() {
            @Override
            public void insertUpdate(DocumentEvent e) {
                TwigSettingsForm.this.changed = true;
            }

            @Override
            public void removeUpdate(DocumentEvent e) {
                TwigSettingsForm.this.changed = true;
            }

            @Override
            public void changedUpdate(DocumentEvent e) {
                TwigSettingsForm.this.changed = true;
            }
        });
    }

    private void attachItems() {
        // @TODO: remove this check, moved init stuff out of constructor
        // dont load on project less context
        if(this.project == null) {
            return;
        }

        List<TwigPath> sortableLookupItems = new ArrayList<>();
        sortableLookupItems.addAll(TwigUtil.getTwigNamespaces(this.project, true));
        Collections.sort(sortableLookupItems);

        for (TwigPath twigPath : sortableLookupItems) {
            // dont use managed class here
            this.modelList.addRow(twigPath.clone());
        }
    }

    @Nls
    @Override
    public String getDisplayName() {
        return "Twig";
    }

    @Nullable
    @Override
    public String getHelpTopic() {
        return null;
    }

    @Nullable
    @Override
    public JComponent createComponent() {

        this.tableView = new TableView<>();
        this.modelList = new ListTableModel<>(
            new NamespaceColumn(),
            new PathColumn(project)
        );

        this.attachItems();

        this.tableView.setModelAndUpdateColumns(this.modelList);

        this.modelList.addTableModelListener(e -> TwigSettingsForm.this.changed = true);

        ToolbarDecorator tablePanel = ToolbarDecorator.createDecorator(this.tableView, new ElementProducer<TwigPath>() {
            @Override
            public TwigPath createElement() {
                //IdeFocusManager.getInstance(TwigSettingsForm.this.project).requestFocus(TwigNamespaceDialog.getWindows(), true);
                return null;  //To change body of implemented methods use File | Settings | File Templates.
            }

            @Override
            public boolean canCreateElement() {
                return true;  //To change body of implemented methods use File | Settings | File Templates.
            }
        });

        tablePanel.setEditAction(anActionButton -> TwigSettingsForm.this.openTwigPathDialog(TwigSettingsForm.this.tableView.getSelectedObject()));


        tablePanel.setAddAction(anActionButton -> TwigSettingsForm.this.openTwigPathDialog(null));

        tablePanel.setEditActionUpdater(e -> {
            TwigPath twigPath = TwigSettingsForm.this.tableView.getSelectedObject();
            return twigPath != null && twigPath.isCustomPath();
        });

        tablePanel.setRemoveActionUpdater(e -> {
            TwigPath twigPath = TwigSettingsForm.this.tableView.getSelectedObject();
            return twigPath != null && twigPath.isCustomPath();
        });

        tablePanel.disableUpAction();
        tablePanel.disableDownAction();

        this.panelTableView.add(tablePanel.createPanel());

        buttonJsonExample.addActionListener(e -> TwigJsonExampleDialog.open(TwigSettingsForm.this.panel1));

        return this.panel1;
    }

    private MouseListener createPathButtonMouseListener(final JTextField textField, final FileChooserDescriptor fileChooserDescriptor) {
        return new MouseListener() {
            @Override
            public void mouseClicked(MouseEvent mouseEvent) {
            }

            @Override
            public void mousePressed(MouseEvent mouseEvent) {
                VirtualFile projectDirectory = project.getBaseDir();
                VirtualFile selectedFile = FileChooser.chooseFile(
                        fileChooserDescriptor,
                        project,
                        VfsUtil.findRelativeFile(textField.getText(), projectDirectory)
                );

                if (null == selectedFile) {
                    return; // Ignore but keep the previous path
                }

                textField.setText(selectedFile.getPath());
            }

            @Override
            public void mouseReleased(MouseEvent mouseEvent) {
            }

            @Override
            public void mouseEntered(MouseEvent mouseEvent) {
            }

            @Override
            public void mouseExited(MouseEvent mouseEvent) {
            }
        };
    }

    @Override
    public boolean isModified() {
        return this.changed;
    }

    @Override
    public void apply() throws ConfigurationException {
        List<TwigNamespaceSetting> twigPaths = new ArrayList<>();

        for(TwigPath twigPath :this.tableView.getListTableModel().getItems()) {
            // only custom and disabled path need to save
            if((!twigPath.isEnabled() && twigPath.getRelativePath(this.project) != null) || twigPath.isCustomPath()) {
                twigPaths.add(new TwigNamespaceSetting(twigPath.getNamespace(), twigPath.getRelativePath(this.project), twigPath.isCustomPath()));
            }
        }

        getSettings().namespacesManifestPath = this.manifestPath.getText();
        getSettings().twigNamespaces = twigPaths;

        this.project.putUserData(JsonFileIndexTwigNamespaces.CACHE, null);

        this.resetList();
        this.attachItems();

        this.changed = false;
    }

    private Settings getSettings() {
        return Settings.getInstance(this.project);
    }

    private void resetList() {
        this.modelList.setItems(new ArrayList<>());
    }

    @Override
    public void reset() {
        this.resetList();
        this.attachItems();

        this.changed = false;
    }

    @Override
    public void disposeUIResources() {
        this.resetList();
    }

    private class NamespaceColumn extends ColumnInfo<TwigPath, String> {

        public NamespaceColumn() {
            super("Namespace");
        }

        @Nullable
        @Override
        public String valueOf(TwigPath twigPath) {
             return twigPath.getNamespace();
        }
    }

    private class PathColumn extends ColumnInfo<TwigPath, String> {

        private Project project;

        public PathColumn(Project project) {
            super("Path");
            this.project = project;
        }

        @Nullable
        @Override
        public String valueOf(TwigPath twigPath) {
            return twigPath.getRelativePath(this.project);
        }
    }

    private abstract class BooleanColumn extends ColumnInfo<TwigPath, Boolean>
    {
        public BooleanColumn(String name) {
            super(name);
        }

        public boolean isCellEditable(TwigPath groupItem)
        {
            return true;
        }

        public Class getColumnClass()
        {
            return Boolean.class;
        }
    }

    private void openTwigPathDialog(@Nullable TwigPath twigPath) {
        TwigNamespaceDialog twigNamespaceDialog;
        if(twigPath == null) {
            twigNamespaceDialog = new TwigNamespaceDialog(project, this.tableView);
        } else {
            twigNamespaceDialog = new TwigNamespaceDialog(project, this.tableView, twigPath);
        }

        Dimension dim = new Dimension();
        dim.setSize(500, 190);
        twigNamespaceDialog.setTitle("Twig Namespace");
        twigNamespaceDialog.setMinimumSize(dim);
        twigNamespaceDialog.pack();
        twigNamespaceDialog.setLocationRelativeTo(TwigSettingsForm.this.panel1);

        twigNamespaceDialog.setVisible(true);
    }

}
