function [diff] = time_diff(list)
    diff = zeros(size(list));
    diff(1) = list(1);
    for j=1:size(list)-1,
        diff(j+1) = list(j+1)-list(j);
    end
end
