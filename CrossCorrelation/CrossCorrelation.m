N = 300; 					%the number of input and output samples
taw= 60; 					%time lag ?
G = zeros(taw*2+1, 1);		%cross correlation
%calculate sigma part of the cross correlation equation
for t = -taw: taw
    if (t >= 0)
        count = 0;                      %counter of the number of variables in G
        for n = 51: N+1
            if (n+t > num || n+t <= 0)
    G(t+taw+1) = G(t+taw+1) + 0;
            else 
                G(t+taw+1) = G(t+taw+1) + u_deg(n)*y(n+t);
                count = count + 1;
            end
        end
        G(t+taw+1) = G(t+taw+1)/count; 	%cross correlation for ? >= 0
    Else                                %for ? < 0
        count = 0;
        for n = 51:N+1
            if (n-t > num || n-t <= 0)
                G(t+taw+1) = G(t+taw+1) + 0;
            else
                G(t+taw+1) = G(t+taw+1) + u_deg (n-t)*y(n);
                count = count + 1;
            end
        end
        G(t+taw+1) = G(t+taw+1)/count;	%cross correlation for ? >= 0
    end
end
% plot G vs time lag ?
lag = -taw:taw;
plot (lag, G)
