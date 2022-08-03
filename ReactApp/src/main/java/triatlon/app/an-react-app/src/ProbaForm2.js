import React from  'react';
class ProbaForm2 extends React.Component{

    constructor(props) {
        super(props);
        this.state = {id:'',nume: '', id_arb:''};

        //  this.handleChange = this.handleChange.bind(this);
        // this.handleSubmit = this.handleSubmit.bind(this);
    }
    handleIdChange=(event) =>{
        this.setState({id: event.target.value});
    }

    handleUserChange=(event) =>{
        this.setState({nume: event.target.value});
    }

    handleNameChange=(event) =>{
        this.setState({id_arb: event.target.value});
    }


    handleSubmit =(event) =>{

        var proba={
            id:this.state.id,
            nume:this.state.nume,
            id_arb:this.state.id_arb
        }
        console.log('A user was submitted: ');
        console.log(proba);
        //this.props.addFunc(proba);
        this.props.updateFunc(proba.id,proba);
        event.preventDefault();
    }


    render() {
        return (
            <form className="form2" onSubmit={this.handleSubmit}>
                <label className="cv">Modifica proba</label>
                <br/>
                <br/>
                <label>
                    Id-ul :
                    <input className="for" type="number" value={this.state.id} onChange={this.handleIdChange} />
                </label><br/>
                <label>
                    Nume:
                    <input className="for" type="text" value={this.state.nume} onChange={this.handleUserChange} />
                </label><br/>
                <label>
                    Id_arb:
                    <input className="for" type="number" value={this.state.cod_arb} onChange={this.handleNameChange} />
                </label><br/>
                <br/>
                <input className="but"  type="submit" value="Modifica"/>
            </form>
        );
    }
}
export default ProbaForm2;