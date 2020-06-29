import React from 'react'
import './Login.css'

export default () => {

    return (
        <div className="login">
            <div className="form">
                <h1>Doce Lar</h1>
                <form className="form-signin">
                    <input className="form-control form-control-lg" type="text" name="usuario" id="user" placeholder="Usuário" />
                    <input className="form-control form-control-lg" type="password" name="senha" id="pass" placeholder="Senha" />
                    <button className="btn btn-lg btn-block btn-primary">Entrar</button>
                    <button className="btn btn-lg btn-block btn-secondary">Esqueci a senha</button>
                </form>
            </div>
        </div>
    )
}