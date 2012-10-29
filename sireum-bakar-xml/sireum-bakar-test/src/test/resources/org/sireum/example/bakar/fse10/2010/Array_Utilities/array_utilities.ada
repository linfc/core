package Array_Utilities is
   Max_Table_Size : constant := 100;
   type Base_Index_Type is range 0..Max_Table_Size;
   subtype Index_Type is Base_Index_Type range 1..Max_Table_Size;
   type Contents_Type is range -1000 .. 1000;
   type Array_Type is array(Index_Type) of Contents_Type;

   --# function Ordered(A : Array_Type; L,U : Index_Type)
   --#   return Boolean;
   --# function Perm(A, B : Array_Type) return Boolean;

   procedure Sort(Table : in out Array_Type);
   --# derives Table from Table;
   --# post Ordered(Table,1,Max_Table_Size) and
   --#   Perm(Table,Table~);
end Array_Utilities;

package body Array_Utilities is
   --# function Partitioned(A : Array_Type;
   --#     L, M, U : Index_Type) return Boolean;

   procedure Sort(Table : in out Array_Type) is
      Key : Index_Type;

      --# function The_Smallest(A : Array_Type;
      --#   L,U : Index_Type) return Index_Type;

      function Find_Smallest(Arr : Array_Type; L,U: Index_Type)
                            return Index_Type
        --# pre 1 <= L and L <= U and U <= Max_Table_Size;
        --# return The_Smallest(Arr,L,U);
        --! return I => (for all J in Index_Type range
        --!        L .. U => (Arr(J) >= Arr(I)));
      is
         K : Index_Type;
      begin
         K := L;
         for I in Index_Type range L+1..U loop
            if Arr(I) < Arr(K) then
               K := I;
            end if;
            --# assert 1 <= L and L+1 <= I and
            --#   I <= U and U <= Max_Table_Size and
            --#   K in Index_Type and
            --#   K = The_Smallest(Arr,L,I);
            --!   (for all J in Index_Type range
            --!     L .. I => (Arr(J) >= Arr(K)));
         end loop;
         return K;
      end Find_Smallest;

      procedure Swap_Elements(T : in out Array_Type;
                              I, J : in Index_Type)
        --# derives T from T,I,J;
        --# post T = T~[I => T~(J); J => T~(I)] and Perm(T,T~);
      is
         Temp : Contents_Type;
      begin
         Temp := T(I); T(I) := T(J); T(J) := Temp;
      end Swap_Elements;

   begin -- Sort
      for Low in Index_Type range 1 .. Max_Table_Size-1 loop
         Key := Find_Smallest(Table,Low,Max_Table_Size);
         if Key /= Low then
            Swap_Elements(Table,Low,Key);
         end if;
         --# assert 1 <= Low and Low <= Max_Table_Size-1 and
         --#      Ordered(Table,1,Low) and
         --#      Partitioned(Table,1,Low,Max_Table_Size) and
         --#      Perm(Table,Table~);
      end loop;
   end Sort;
end Array_Utilities;

