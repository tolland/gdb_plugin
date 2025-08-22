package org.limepepper.lang.gdb.injection

import com.intellij.openapi.application.ApplicationManager
import com.intellij.openapi.components.Service
import com.intellij.openapi.project.Project
import com.intellij.openapi.startup.ProjectActivity
import com.intellij.openapi.vfs.VfsUtil
import com.intellij.util.PathUtil
import java.io.File

@Service(Service.Level.PROJECT)
class GdbPythonPathService(private val project: Project) {
    
    companion object {
        fun getInstance(project: Project): GdbPythonPathService = 
            project.getService(GdbPythonPathService::class.java)
    }
    
    fun getStubsPath(): String? {
        return try {
            // Get the plugin's resources directory
            val pluginPath = PathUtil.getJarPathForClass(this::class.java)
            val stubsPath = if (pluginPath?.endsWith(".jar") == true) {
                // Running from built plugin
                "jar:file:$pluginPath!/python-stubs"
            } else {
                // Running in development
                val resourcesDir = File(pluginPath).parent + "/resources/python-stubs"
                if (File(resourcesDir).exists()) resourcesDir else null
            }
            stubsPath
        } catch (e: Exception) {
            null
        }
    }
}

class GdbPythonPathStartupActivity : ProjectActivity {
    override suspend fun execute(project: Project) {
        // Initialize the service on project startup
        GdbPythonPathService.getInstance(project)
        
        // Attempt to configure Python path for PyCharm/IntelliJ with Python plugin
        try {
            configurePythonPath(project)
        } catch (e: Exception) {
            // Silently ignore if Python plugin is not available
        }
    }
    
    private fun configurePythonPath(project: Project) {
        val service = GdbPythonPathService.getInstance(project)
        val stubsPath = service.getStubsPath() ?: return
        
        // This would require the Python plugin to be present
        // For now, we'll just ensure our stubs are available
        ApplicationManager.getApplication().runWriteAction {
            try {
                val url = java.net.URL(VfsUtil.pathToUrl(stubsPath))
                val stubsVfs = VfsUtil.findFileByURL(url)
                // The Python plugin would handle adding this to its module path
                // if it's available, but we don't depend on it being present
            } catch (e: Exception) {
                // Ignore - Python plugin may not be available
            }
        }
    }
}