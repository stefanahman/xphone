function [return_list] = expectation_value_normal(list)
    my = mean(list);
    sigma_squared = var(list);
    return_list = [my,sigma_squared];
end