function [lambda] = expectation_value_exponential(list)
    list_mean = mean(list);
    lambda = 1/list_mean;
end