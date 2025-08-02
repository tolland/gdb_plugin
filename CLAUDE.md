# CLAUDE.md

This file provides guidance to Claude Code (claude.ai/code) when working with code in this repository.

## validate changes

validate code changes at the end of each change.

```bash
./gradlew buildPlugin
```

## Commit each task change

Commit changes to current branch as "checkpoint" commit. Prefix commit with "checkpoint: some description"

if committing files, to clean working directory, commit message "checkpoint" is sufficient. The purpose is to achieve a commit per change.

## Project Overview

This is a JetBrains IntelliJ Platform plugin written in Kotlin that provides GDB debugger script support for CLion and PyCharm. The plugin implements custom language support including file type recognition, syntax highlighting, formatting, and documentation features.

## Development Commands

**Build and Test:**
```bash
./gradlew build                    # Build the plugin
./gradlew test                     # Run tests
./gradlew buildPlugin             # Build plugin distribution
./gradlew runIde                  # Run plugin in development IDE instance
```

**Development:**
```bash
./gradlew verifyPlugin            # Verify plugin structure and compatibility
./gradlew publishPlugin           # Publish to JetBrains marketplace (requires token)
```

## Architecture

The plugin follows IntelliJ Platform's language support architecture with these key components:

### Phase 1: File Type Recognition
- `GdbFileType` - Defines the .gdb file type
- `GdbFileTypeFactory` - Registers the file type with the platform

### Phase 2-3: Syntax Highlighting
- `GdbLanguage` - Language definition extending `Language`
- `GdbLexer` - Token lexer for GDB syntax (using JFlex)
- `GdbSyntaxHighlighter` - Syntax highlighting implementation
- `GdbSyntaxHighlighterFactory` - Factory for syntax highlighter
- `GdbColorSettingsPage` - Color scheme configuration page

### Phase 4: Code Formatting
- `GdbFormattingModelBuilder` - Builds formatting model
- `GdbBlock` - Formatting block implementation
- `GdbCodeStyleSettings` - Code style configuration

### Phase 5: Documentation
- `GdbDocumentationProvider` - Provides documentation for PSI elements
- PSI structure classes for representing GDB syntax elements

## Key IntelliJ Platform APIs

**File Type Support:**
- `LanguageFileType` - Base class for custom file types
- `FileTypeFactory` - Deprecated, use `FileTypeRegistry` extension

**Language Support:**
- `Language` - Base language class
- `ParserDefinition` - Defines grammar and lexer
- `SyntaxHighlighter` - Syntax highlighting interface
- `SyntaxHighlighterFactory` - Factory pattern for highlighters

**PSI (Program Structure Interface):**
- `PsiElement` - Base interface for syntax tree elements
- `PsiFile` - Root PSI element for files
- `ElementType` - Token and node types

**Modern Extension Points (non-deprecated):**
- Use `<fileType>` extension point instead of `FileTypeFactory`
- Use `<lang.syntaxHighlighterFactory>` for syntax highlighting
- Use `<lang.formatter>` for code formatting
- Use `<lang.documentationProvider>` for documentation

## Development Notes

- Target IntelliJ Platform 2025.1+ for latest APIs
- Use Kotlin coroutines for async operations where applicable
- Use `@Service` annotation for services instead of deprecated component system
- Prefer extension points over direct registration where possible
