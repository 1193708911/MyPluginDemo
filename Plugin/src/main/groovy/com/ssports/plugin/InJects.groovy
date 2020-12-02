package com.ssports.plugin


import javassist.ClassPool
import javassist.CtClass
import javassist.CtMethod
import org.gradle.api.Project

class InJects {

    //首先加载类路径
    static ClassPool classPool = new ClassPool(ClassPool.getDefault())

    private static String packName = "com.hhb.myplugindemo";

    static void injectDir(String fileInPath, Project project) {

        classPool.appendClassPath(fileInPath)

        classPool.appendClassPath(project.android.bootClasspath[0].toString())


        File dir = new File(fileInPath)

        if (dir.isDirectory()) {

            dir.eachFileRecurse { File file ->
                String filePath = file.getAbsolutePath();
                printf("当前文件的路径为" + filePath)
                if (file.getName().equals("MainActivity.class")) {//拦截到了
//                    CtClass mainCtClass = classPool.getCtClass("com.hhb.myplugindemo.MainActivity")
                    //截取当前的.class
                    String absoluteDotPath = file.getAbsolutePath().substring(0, filePath.length() - 6).replaceAll("/", ".")
                    int index = absoluteDotPath.indexOf(packName)

                    if (index >= 0) {
                        CtClass mainCtClass = classPool.getCtClass(absoluteDotPath.substring(index, absoluteDotPath.length()))
                        //解冻
                        if (mainCtClass.isFrozen()) {
                            mainCtClass.defrost()
                        }
                        CtMethod[] ctMethods = mainCtClass.getDeclaredMethods()
                        CtClass CtAnnotations = classPool.getCtClass("com.hhb.annotation.DotAnnotation")
                        Class aNotationClass = CtAnnotations.toClass()
                        for (CtMethod ctMethod : ctMethods) {
                            Object mObject = ctMethod.getAnnotation(aNotationClass)

                            if (mObject != null) {
                                //开始植入代码
                                String insertCode = "android.widget.Toast.makeText(this,\"当前植入的代码为\",android.widget.Toast.LENGTH_SHORT).show();"
                                ctMethod.insertBefore(insertCode)
                                mainCtClass.writeFile(fileInPath)
                                mainCtClass.detach()
                            }
                        }
                    }

                }

            }
        }
    }

}