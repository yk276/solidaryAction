import { createSlice, PayloadAction } from "@reduxjs/toolkit";
import Cookies from "js-cookie";
import { login, logout } from "./authSlice";

interface FavoritosState {
    usuarioId: number | null;
    campanhasIds: number[];
}

//Recupera os favoritos salvos para o usuário.
function recuperarFavoritos(usuarioId: number | null): number[] {

    if (usuarioId == null) {
        return [];
    }

    const favoritosSalvos = Cookies.get("favoritos_" + usuarioId);

    if (!favoritosSalvos) {
        return [];
    }

    try {
        const ids = JSON.parse(favoritosSalvos);

        if (Array.isArray(ids)) {
            return ids.filter(
                (id) => typeof id === "number"
            );
        }

        return [];

    } catch (error) {
        return [];
    }
}

//Identifica a sessão recuperada dos cookies ao atualizar a página.
let usuarioIdInicial: number | null = null;

const usuarioSalvo = Cookies.get("usuario");

if (usuarioSalvo) {
    try {
        const usuario = JSON.parse(usuarioSalvo);

        if (typeof usuario.id === "number") {
            usuarioIdInicial = usuario.id;
        }

    } catch (error) {
        usuarioIdInicial = null;
    }
}

const initialState: FavoritosState = {
    usuarioId: usuarioIdInicial,
    campanhasIds: recuperarFavoritos(usuarioIdInicial)
};

const favoritosSlice = createSlice({
    name: "favoritos",
    initialState,
    reducers: {
        alternarFavorito: (
            state,
            action: PayloadAction<number>
        ) => {

            if (state.usuarioId == null) {
                return;
            }

            const campanhaId = action.payload;

            if (state.campanhasIds.includes(campanhaId)) {
                state.campanhasIds = state.campanhasIds.filter(
                    (id) => id !== campanhaId
                );
            } else {
                state.campanhasIds.push(campanhaId);
            }

            Cookies.set(
                "favoritos_" + state.usuarioId,
                JSON.stringify(state.campanhasIds),
                {expires: 7}
            );
        }
    },
    extraReducers: (builder) => {
        builder
            .addCase(login, (state, action) => {
                state.usuarioId = action.payload.usuario.id;

                state.campanhasIds = recuperarFavoritos(
                    state.usuarioId
                );
            })
            .addCase(logout, (state) => {
                state.usuarioId = null;
                state.campanhasIds = [];
            });
    }
});

export const {alternarFavorito} = favoritosSlice.actions;
export default favoritosSlice.reducer;