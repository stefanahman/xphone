function [b] = expectation_value_uniform(list)
    list_size = length(list);
    b = (list_size+1)/list_size * max(list);
end