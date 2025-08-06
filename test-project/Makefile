.PHONY: clean build

.DEFAULT_GOAL := help

help:
	python -c "$$PRINT_HELP_PYSCRIPT" < $(MAKEFILE_LIST)

clean:
	rm -rf build/*

tox: ## run tox on docker compose
	@echo do tox stuff ## this is a comment
	tox

# This is at ets
test: ## run run pytest
	poetry run pytest

