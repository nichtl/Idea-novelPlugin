package com.nicht.fishbook.factory;

import com.intellij.openapi.project.Project;
import com.intellij.openapi.wm.ToolWindow;
import com.intellij.openapi.wm.ToolWindowFactory;
import com.intellij.ui.content.Content;
import com.intellij.ui.content.ContentFactory;

import com.nicht.fishbook.service.BookScanner;
import com.nicht.fishbook.service.BookScannerBuilder;
import com.nicht.fishbook.service.PersistentState;
import com.nicht.fishbook.ui.ReadUI;
import org.jetbrains.annotations.NotNull;


public class ReadWindowFactory implements ToolWindowFactory {

    public static ReadUI readUI;
    @Override
    public void createToolWindowContent(@NotNull Project project, @NotNull ToolWindow toolWindow) {
        //创建出NoteListWindow对象
        readUI = new ReadUI(project, toolWindow);
        //获取内容工厂的实例
        ContentFactory contentFactory = ContentFactory.SERVICE.getInstance();
        //获取用于toolWindow显示的内容
        Content content = contentFactory.createContent(readUI.getJcontent(), "", false);
        //给toolWindow设置内容
        toolWindow.getContentManager().addContent(content);
        BookScanner bookScanner = BookScannerBuilder.builder(null);
        if(bookScanner!=null){
            readUI.turnPage(PersistentState.getInstance().getPageNum());
        }
    }
}
