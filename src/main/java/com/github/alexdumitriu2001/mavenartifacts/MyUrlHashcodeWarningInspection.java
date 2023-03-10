package com.github.alexdumitriu2001.mavenartifacts;

import com.intellij.codeInspection.AbstractBaseJavaLocalInspectionTool;
import com.intellij.codeInspection.LocalQuickFix;
import com.intellij.codeInspection.ProblemDescriptor;
import com.intellij.codeInspection.ProblemsHolder;
import com.intellij.openapi.project.Project;
import com.intellij.psi.*;
import org.jetbrains.annotations.NotNull;

import java.util.Objects;

/**
 * Implements an inspection to detect when .hashCode() is used from a URL instance.
 * The quick fix converts the URL into a URI in order to avoid the possibility of triggering a name service.
 */
public class MyUrlHashcodeWarningInspection extends AbstractBaseJavaLocalInspectionTool {

    private final ReplaceWithUriQuickFix myQuickFix = new ReplaceWithUriQuickFix();

    /**
     * This method is overridden to provide a custom visitor
     * that inspects expressions with Url.hashCode().
     * The visitor must not be recursive and must be thread-safe.
     *
     * @param holder     object for the visitor to register problems found
     * @param isOnTheFly true if inspection was run in non-batch mode
     * @return non-null visitor for this inspection
     * @see JavaElementVisitor
     */
    @NotNull
    @Override
    public PsiElementVisitor buildVisitor(@NotNull final ProblemsHolder holder, boolean isOnTheFly) {
        return new JavaElementVisitor() {

            /**
             * Evaluate binary psi expressions to see if they contain relational operators '==' and '!=',
             * AND they are of String type.
             * The evaluation ignores expressions comparing an object to null.
             * IF these criteria are met, register the problem in the ProblemsHolder.
             *
             * @param expression The method call expression to be evaluated.
             */
            @Override
            public void visitMethodCallExpression(PsiMethodCallExpression expression) {
                super.visitMethodCallExpression(expression);
                System.out.println(expression);
                PsiClass classCaller = Objects.requireNonNull(expression.resolveMethod()).getContainingClass();
                String methodName = expression.getMethodExpression().toString();
                assert classCaller != null;
                if (Objects.equals(classCaller.toString(), "PsiClass:URL")
                        && methodName.contains("hashCode")) {
                    holder.registerProblem(expression,
                                MyBundle.message("inspection.url.hashcode.references.problem.descriptor"),
                                myQuickFix);
                }
            }

        };
    }

    /**
     * This class provides a solution to inspection problem expressions by manipulating the PSI tree to use 'a.toURI()'.
     */
    private static class ReplaceWithUriQuickFix implements LocalQuickFix {

        /**
         * Returns a partially localized string for the quick fix intention.
         *
         * @return Quick fix short name.
         */
        @NotNull
        @Override
        public String getName() {
            return MyBundle.message("inspection.url.hashcode.references.use.quickfix");
        }

        /**
         * This method manipulates the PSI tree to add .toURI().
         *
         * @param project    The project that contains the file being edited.
         * @param descriptor A problem found by this inspection.
         */
        public void applyFix(@NotNull Project project, @NotNull ProblemDescriptor descriptor) {
            PsiMethodCallExpression methodCallExpression = (PsiMethodCallExpression) descriptor.getPsiElement();
            PsiExpression classCaller = methodCallExpression.getMethodExpression().getQualifierExpression();

            PsiElementFactory factory = JavaPsiFacade.getInstance(project).getElementFactory();
            PsiMethodCallExpression toURICall =
                    (PsiMethodCallExpression) factory.createExpressionFromText("a.toURI()", null);
            PsiMethodCallExpression uriAppliedCall =
                    (PsiMethodCallExpression) factory.createExpressionFromText("b.hashCode()", null);

            assert classCaller != null;
            Objects.requireNonNull(toURICall.getMethodExpression().getQualifierExpression()).replace(classCaller);
            Objects.requireNonNull(uriAppliedCall.getMethodExpression().getQualifierExpression()).replace(toURICall);

            PsiExpression result = (PsiExpression) methodCallExpression.replace(uriAppliedCall);
        }

        @NotNull
        public String getFamilyName() {
            return getName();
        }

    }

}
