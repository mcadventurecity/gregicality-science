package gregicality.science.loaders.recipe.chain;

import gregtech.common.items.MetaItems;

import static gregicality.science.api.recipes.GCYSRecipeMaps.*;
import static gregicality.science.api.unification.materials.GCYSMaterials.*;
import static gregtech.api.GTValues.*;
import static gregtech.api.recipes.RecipeMaps.*;
import static gregtech.api.unification.material.Materials.*;
import static gregtech.api.unification.ore.OrePrefix.*;

public class BoronNitrideChain {

    public static void init() {
        boronTrioxide();
        borazine();
        boronNitride();
    }

    private static void boronTrioxide() {
        // Na2B4O7·10H2O + 2HCl -> 4H3BO3 + 2NaCl + 5H2O
        CHEMICAL_BATH_RECIPES.recipeBuilder()
                .input(dust, Borax, 23)
                .fluidInputs(HydrochloricAcid.getFluid(2000))
                .output(dust, BoricAcid, 16)
                .output(dust, Salt, 4)
                .fluidOutputs(Water.getFluid(5000))
                .duration(300)
                .EUt(VA[MV])
                .buildAndRegister();

        // H3BO3 -> HBO2 + H2O
        DRYER_RECIPES.recipeBuilder()
                .input(dust, BoricAcid, 4)
                .output(dust, MetaboricAcid, 4)
                .fluidOutputs(Water.getFluid(1000))
                .duration(50)
                .EUt(VA[MV])
                .buildAndRegister();

        // 2HBO2 -> B2O3 + H2O
        DRYER_RECIPES.recipeBuilder()
                .input(dust, MetaboricAcid, 8)
                .output(dust, BoronTrioxide, 5)
                .fluidOutputs(Water.getFluid(1000))
                .duration(100)
                .EUt(VA[MV])
                .buildAndRegister();
    }

    private static void borazine() {
        // B2O3 + 6HF -> 2BF3 + 3H2O
        CHEMICAL_BATH_RECIPES.recipeBuilder()
                .input(dust, BoricAcid, 5)
                .fluidInputs(HydrofluoricAcid.getFluid(6000))
                .fluidOutputs(BoronTrifluoride.getFluid(2000))
                .output(dust, Ice, 3) // TODO remove bath minimum item outputs
//                .fluidOutputs(Water.getFluid(3000)) //TODO more fluid slots in bath
                .duration(160)
                .EUt(VA[HV])
                .buildAndRegister();

        // Li + H -> LiH
        BLAST_RECIPES.recipeBuilder()
                .input(dust, Lithium)
                .fluidInputs(Hydrogen.getFluid(1000))
                .output(ingot, LithiumHydride)
                .blastFurnaceTemp(873)
                .duration(300)
                .EUt(VA[HV])
                .buildAndRegister();

        // 8BF3 + 6LiH -> B2H6 + 6LiBF4
        CHEMICAL_RECIPES.recipeBuilder()
                .input(dust, LithiumHydride, 12)
                .fluidInputs(BoronTrifluoride.getFluid(8000))
                .output(dust, LithiumTetrafluoroborate, 12)
                .fluidOutputs(Diborane.getFluid(1000))
                .duration(640)
                .EUt(VA[HV])
                .buildAndRegister();

        // LiBF4 -> LiH + BF3
        DRYER_RECIPES.recipeBuilder()
                .input(dust, LithiumTetrafluoroborate, 6)
                .output(ingot, LithiumHydride, 2)
                .fluidOutputs(BoronTrifluoride.getFluid(1000))
                .duration(80)
                .EUt(VA[LV])
                .buildAndRegister();

        // 3B2H6 + 6NH3 -> 2B3H6N3 + 24H
        CHEMICAL_RECIPES.recipeBuilder()
                .fluidInputs(Diborane.getFluid(3000))
                .fluidInputs(Ammonia.getFluid(6000))
                .fluidOutputs(Borazine.getFluid(2000))
                .fluidOutputs(Hydrogen.getFluid(24000))
                .duration(400)
                .EUt(VA[LuV])
                .buildAndRegister();

        // B2O3 + 3C + 6Cl -> 2BCl3 + 3CO
        CHEMICAL_RECIPES.recipeBuilder()
                .input(dust, BoronTrioxide, 5)
                .input(dust, Carbon, 3)
                .fluidInputs(Chlorine.getFluid(6000))
                .fluidOutputs(BoronTrichloride.getFluid(2000))
                .fluidOutputs(CarbonMonoxide.getFluid(3000))
                .duration(90) //TODO 774K
                .EUt(VA[HV])
                .buildAndRegister();

        // 3BCl3 + 3NH4Cl -> B3Cl3H3N3 + 9HCl
        CHEMICAL_RECIPES.recipeBuilder()
                .input(dust, AmmoniumChloride, 6)
                .fluidInputs(BoronTrichloride.getFluid(3000))
                .fluidOutputs(Trichloroborazine.getFluid(1000))
                .fluidOutputs(HydrochloricAcid.getFluid(9000))
                .duration(400)
                .EUt(VA[IV])
                .buildAndRegister();
    }

    private static void boronNitride() {

        // B3H6N3 + 3O -> 3BN + 3H2O
        CVD_RECIPES.recipeBuilder()
                .fluidInputs(Borazine.getFluid(1000))
                .fluidInputs(Oxygen.getFluid(3000))
                .output(gem, HexagonalBoronNitride, 6)
                .fluidOutputs(Water.getFluid(3000))
                .duration(400) //TODO Ultra High Vacuum
                .EUt(VA[UV])
                .buildAndRegister();

        // B + N -> BN
        MOLECULAR_BEAM_RECIPES.recipeBuilder()
                .input(foil, Nickel, 8)
                .input(dust, Boron)
                .fluidInputs(Nitrogen.getFluid(1000))
                .output(gem, HexagonalBoronNitride, 2)
                .blastFurnaceTemp(2900)
                .duration(80)
                .EUt(VA[UEV])
                .buildAndRegister();

        // h-BN -> c-BN
        CVD_RECIPES.recipeBuilder()
                .input(dust, HexagonalBoronNitride)
                .output(gem, CubicBoronNitride)
                .duration(100) //TODO 18GPa, 3501K
                .EUt(VA[UV])
                .buildAndRegister();

        // B3Cl3H3N3 -> 3 a-BN + 3HCl
        CHEMICAL_RECIPES.recipeBuilder()
                .input(dust, Caesium)
                .fluidInputs(Trichloroborazine.getFluid(1000))
                .output(dust, AmorphousBoronNitride, 6)
                .fluidOutputs(HydrochloricAcid.getFluid(3000))
                .duration(200)
                .EUt(VA[ZPM])
                .buildAndRegister();

        // c-BN + C -> BCN
        IMPLOSION_RECIPES.recipeBuilder()
                .input(dust, CubicBoronNitride)
                .input(dust, Carbon)
                .output(gem, Heterodiamond, 2)
                .explosivesAmount(32)
                .duration(20)
                .EUt(VA[LV])
                .buildAndRegister();

        // c-BN + C -> BCN
        IMPLOSION_RECIPES.recipeBuilder()
                .input(dust, CubicBoronNitride)
                .input(dust, Carbon)
                .output(gem, Heterodiamond, 2)
                .explosivesType(MetaItems.DYNAMITE.getStackForm(16))
                .duration(20)
                .EUt(VA[LV])
                .buildAndRegister();

        // BCN + C -> c-BC2N
        CVD_RECIPES.recipeBuilder()
                .input(dust, Heterodiamond)
                .input(dust, Carbon)
                .output(gem, CubicHeterodiamond)
                .duration(400) //TODO 18GPa, 2200K
                .EUt(VA[UHV])
                .buildAndRegister();
    }
}
