package net.bolbat.utils.common;

public interface ManagedService {

	int getExceptionExecutions();

	int getPostConstructExecutions();

	int getPreDestroyExecutions();

	int getExecuteExecutions();

}
