import { React } from 'react';
import { Form } from 'react-bootstrap';
import changeSizeAction from '../../../store/actions/ChangeSizeAction';
import { connect } from 'react-redux';

function CountItems({ setSize }) {
    const defaultValueSize = 10;

    const handleChangeSize = (e) => {
        setSize(e.target.value);
    }

    return (
        <Form.Select aria-label="Default select example" fixed="bottom" style={{ margin: '10px 0 0 0' }}
            defaultValue={defaultValueSize} onChange={handleChangeSize}>
            <option value="10">10</option>
            <option value="20">20</option>
            <option value="50">50</option>
        </Form.Select>
    );
}
/*function mapStateToProps(state) {
    return {
        size: state.size,
        page: state.page,
        count: state.totalNumberItems
    }
}*/

function mapDispatchToProps(dispatch) {
    return {
        setSize: (size) => {
            dispatch(changeSizeAction(size))
        }
    }
}

export default connect(mapDispatchToProps)(CountItems);