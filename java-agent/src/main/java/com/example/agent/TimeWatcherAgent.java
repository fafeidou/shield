package com.example.agent;

import javassist.*;
import org.apache.log4j.Logger;

import java.io.IOException;
import java.lang.instrument.ClassFileTransformer;
import java.lang.instrument.Instrumentation;
import java.lang.instrument.UnmodifiableClassException;
import java.security.ProtectionDomain;

/**
 * 先运行 com.example.agent.TestMain
 * 在运行 com.example.agent.TimeWatcherAgent
 * 可以发现trace 方法耗时生效了 看到打印，说明生效了 [Application] test completed in:3 seconds!
 */
public class TimeWatcherAgent {
    public static Logger LOGGER = Logger.getLogger(TimeWatcherAgent.class);

    public static void agentmain(String agentArgs, Instrumentation instrumentation) throws UnmodifiableClassException {
        instrumentation.addTransformer(new TimeWatcherTransformer(), true);
        instrumentation.retransformClasses(TestMain.class);
    }

    static class TimeWatcherTransformer implements ClassFileTransformer {

        @Override
        public byte[] transform(ClassLoader loader, String className, Class<?> classBeingRedefined, ProtectionDomain protectionDomain, byte[] classfileBuffer) {
            byte[] byteCode = classfileBuffer;
            String finalTargetClassName = "com.example.agent.TestMain".replaceAll("\\.", "/"); //replace . with /
            if (!className.equals(finalTargetClassName)) {
                return byteCode;
            }

            if (className.equals(finalTargetClassName)) {
                LOGGER.info("[Agent] Transforming class TestMain");
                try {
                    ClassPool cp = ClassPool.getDefault();
                    CtClass cc = cp.get("com.example.agent.TestMain");
                    CtMethod m = cc.getDeclaredMethod("test");
                    m.addLocalVariable("startTime", CtClass.longType);
                    m.insertBefore("startTime = System.currentTimeMillis();");

                    StringBuilder endBlock = new StringBuilder();

                    m.addLocalVariable("endTime", CtClass.longType);
                    m.addLocalVariable("opTime", CtClass.longType);
                    endBlock.append("endTime = System.currentTimeMillis();");
                    endBlock.append("opTime = (endTime-startTime)/1000;");

                    endBlock.append("LOGGER.info(\"[Application] test completed in:\" + opTime + \" seconds!\");");

                    m.insertAfter(endBlock.toString());

                    byteCode = cc.toBytecode();
                    cc.detach();
                } catch (NotFoundException | CannotCompileException | IOException e) {
                    LOGGER.error("Exception", e);
                }
            }
            return byteCode;
        }
    }

}
