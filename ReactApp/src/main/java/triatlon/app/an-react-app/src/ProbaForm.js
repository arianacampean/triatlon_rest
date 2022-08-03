import React from  'react';
class ProbaForm extends React.Component{

    constructor(props) {
        super(props);
        this.state = {nume: '', id_arb:''};

    }

    handleUserChange=(event) =>{
        this.setState({nume: event.target.value});
    }

    handleNameChange=(event) =>{
        this.setState({id_arb: event.target.value});
    }

    handleSubmit =(event) =>{

        var proba={
            nume:this.state.nume,
            id_arb:this.state.id_arb
        }
        console.log('A user was submitted: ');
        console.log(proba);
        this.props.addFunc(proba);
        event.preventDefault();
    }



    render() {
        return (
            <form className="formm" onSubmit={this.handleSubmit} >
                <div id="ad">Adauga probe:</div>
                <br/>
                <label>
                    Nume:
                    <input className="for" type="text" value={this.state.nume} onChange={this.handleUserChange} />
                </label><br/>
                <label>
                    Id_arb:
                    <input className="for" type="number" value={this.state.cod_arb} onChange={this.handleNameChange} />
                </label><br/>
                <br/>
                <input className="but" type="submit" value="Salveaza" />

            </form>
        );
    }
}
export default ProbaForm;