package body Example1
is

  subtype Index is Integer range 1..10;
  type ArrayType is array(Index) of Integer;

  function dummy return Integer is begin
    return 1;
  end dummy;

  function Q(A: in Integer) return Integer
  --# return 2 * A;
  is begin
    return A + 1;
  end Q;

  procedure P(A: in ArrayType; S: in Integer; T: out Integer)
    --# derives T from A, S;
    --# pre S >= 5 and S < 10;  -- constrain S to create example of initial state
    --# post T >= 0;   -- true when bounds violated; false when not.
                    -- When bounds on A[I] in conditional are violated,
                    -- T >= 0  due to T := Q(S) assignment
                    --    (other assignments to T are not processed).
                    -- When bounds are not violated, T's value is based
                    -- on A which is unconstrained (possible negative)
  is
    I : Index;
  begin
   T := Q(S);          -- T = 2 * S;
   --# assert(T < 20); -- completely verified; no need to re-check for higher k
   I := S;             -- implicit range check for I (which succeeds)
   if (A(I) = T) then  -- S >= 5, so bounds violation if k = 4..9; no k violation if k=10;
     T := (A(I) * 2) + 2 ;    -- T = A[S] * 2;
   else
     --# accept Flow, 10, "condition is never satisfied since T = 2 * S and S >= 5";
     if (S = T) then
        --# assert((S + A(1)) < T);  -- unreachable assertion
        T := 0;
     end if;
     T := A(I) * 4;   -- T = A[S] * 4;
   end if;
  end P;

end Example1;