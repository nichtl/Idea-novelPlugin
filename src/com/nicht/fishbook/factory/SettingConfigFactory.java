package com.nicht.fishbook.factory;

import com.intellij.openapi.options.ConfigurationException;
import com.intellij.openapi.options.SearchableConfigurable;

import com.nicht.fishbook.service.BookScanner;
import com.nicht.fishbook.service.BookScannerBuilder;
import com.nicht.fishbook.service.PersistentState;
import com.nicht.fishbook.ui.SettingUI;
import org.jetbrains.annotations.Nls;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import javax.swing.*;
import java.io.File;


public class SettingConfigFactory implements SearchableConfigurable {
    private SettingUI settingUI;

    private PersistentState persistentState = PersistentState.getInstance();

    @NotNull
    @Override
    public String getId() {
        return "fish.id";
    }

    @Nls(capitalization = Nls.Capitalization.Title)
    @Override
    public String getDisplayName() {
        return "fish-book-config";
    }

    @Nullable
    @Override
    public JComponent createComponent() {
        if (settingUI == null) {
            settingUI = new SettingUI();
        }
        settingUI.init(persistentState);
        return settingUI.settingPanel;
    }

    @Override
    public boolean isModified() {
        return true;
    }

    @Override
    public void apply() throws ConfigurationException {
        String bookPath = settingUI.tfBookPath.getText();
        if(bookPath==null){
            return;
        }
        String oldBookPathText = persistentState.getBookPathText();
        persistentState.setBookPathText(bookPath);
        if (settingUI.tfPageSize.getText() != null&&!"".equals(settingUI.tfPageSize.getText())) {
            int i = Integer.parseInt(settingUI.tfPageSize.getText());
            persistentState.setPageSize(i);
        }
        if(oldBookPathText==null){
            BookScannerBuilder.builder(bookPath);
            ReadWindowFactory.readUI.turnPage(1);
            return;
        }
        if(!bookPath.equals(oldBookPathText)){
            BookScanner bookScanner = BookScannerBuilder.rebuild(bookPath);
            ReadWindowFactory.readUI.turnPage(1);
        }
    }
}

