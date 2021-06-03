import axios from 'axios';
import { ICrudGetAction, ICrudGetAllAction, ICrudPutAction, ICrudDeleteAction } from 'react-jhipster';

import { cleanEntity } from 'app/shared/util/entity-utils';
import { REQUEST, SUCCESS, FAILURE } from 'app/shared/reducers/action-type.util';

import { IDiagnosisSequence, defaultValue } from 'app/shared/model/diagnosis-sequence.model';

export const ACTION_TYPES = {
  FETCH_DIAGNOSISSEQUENCE_LIST: 'diagnosisSequence/FETCH_DIAGNOSISSEQUENCE_LIST',
  FETCH_DIAGNOSISSEQUENCE: 'diagnosisSequence/FETCH_DIAGNOSISSEQUENCE',
  CREATE_DIAGNOSISSEQUENCE: 'diagnosisSequence/CREATE_DIAGNOSISSEQUENCE',
  UPDATE_DIAGNOSISSEQUENCE: 'diagnosisSequence/UPDATE_DIAGNOSISSEQUENCE',
  PARTIAL_UPDATE_DIAGNOSISSEQUENCE: 'diagnosisSequence/PARTIAL_UPDATE_DIAGNOSISSEQUENCE',
  DELETE_DIAGNOSISSEQUENCE: 'diagnosisSequence/DELETE_DIAGNOSISSEQUENCE',
  RESET: 'diagnosisSequence/RESET',
};

const initialState = {
  loading: false,
  errorMessage: null,
  entities: [] as ReadonlyArray<IDiagnosisSequence>,
  entity: defaultValue,
  updating: false,
  updateSuccess: false,
};

export type DiagnosisSequenceState = Readonly<typeof initialState>;

// Reducer

export default (state: DiagnosisSequenceState = initialState, action): DiagnosisSequenceState => {
  switch (action.type) {
    case REQUEST(ACTION_TYPES.FETCH_DIAGNOSISSEQUENCE_LIST):
    case REQUEST(ACTION_TYPES.FETCH_DIAGNOSISSEQUENCE):
      return {
        ...state,
        errorMessage: null,
        updateSuccess: false,
        loading: true,
      };
    case REQUEST(ACTION_TYPES.CREATE_DIAGNOSISSEQUENCE):
    case REQUEST(ACTION_TYPES.UPDATE_DIAGNOSISSEQUENCE):
    case REQUEST(ACTION_TYPES.DELETE_DIAGNOSISSEQUENCE):
    case REQUEST(ACTION_TYPES.PARTIAL_UPDATE_DIAGNOSISSEQUENCE):
      return {
        ...state,
        errorMessage: null,
        updateSuccess: false,
        updating: true,
      };
    case FAILURE(ACTION_TYPES.FETCH_DIAGNOSISSEQUENCE_LIST):
    case FAILURE(ACTION_TYPES.FETCH_DIAGNOSISSEQUENCE):
    case FAILURE(ACTION_TYPES.CREATE_DIAGNOSISSEQUENCE):
    case FAILURE(ACTION_TYPES.UPDATE_DIAGNOSISSEQUENCE):
    case FAILURE(ACTION_TYPES.PARTIAL_UPDATE_DIAGNOSISSEQUENCE):
    case FAILURE(ACTION_TYPES.DELETE_DIAGNOSISSEQUENCE):
      return {
        ...state,
        loading: false,
        updating: false,
        updateSuccess: false,
        errorMessage: action.payload,
      };
    case SUCCESS(ACTION_TYPES.FETCH_DIAGNOSISSEQUENCE_LIST):
      return {
        ...state,
        loading: false,
        entities: action.payload.data,
      };
    case SUCCESS(ACTION_TYPES.FETCH_DIAGNOSISSEQUENCE):
      return {
        ...state,
        loading: false,
        entity: action.payload.data,
      };
    case SUCCESS(ACTION_TYPES.CREATE_DIAGNOSISSEQUENCE):
    case SUCCESS(ACTION_TYPES.UPDATE_DIAGNOSISSEQUENCE):
    case SUCCESS(ACTION_TYPES.PARTIAL_UPDATE_DIAGNOSISSEQUENCE):
      return {
        ...state,
        updating: false,
        updateSuccess: true,
        entity: action.payload.data,
      };
    case SUCCESS(ACTION_TYPES.DELETE_DIAGNOSISSEQUENCE):
      return {
        ...state,
        updating: false,
        updateSuccess: true,
        entity: {},
      };
    case ACTION_TYPES.RESET:
      return {
        ...initialState,
      };
    default:
      return state;
  }
};

const apiUrl = 'api/diagnosis-sequences';

// Actions

export const getEntities: ICrudGetAllAction<IDiagnosisSequence> = (page, size, sort) => ({
  type: ACTION_TYPES.FETCH_DIAGNOSISSEQUENCE_LIST,
  payload: axios.get<IDiagnosisSequence>(`${apiUrl}?cacheBuster=${new Date().getTime()}`),
});

export const getEntity: ICrudGetAction<IDiagnosisSequence> = id => {
  const requestUrl = `${apiUrl}/${id}`;
  return {
    type: ACTION_TYPES.FETCH_DIAGNOSISSEQUENCE,
    payload: axios.get<IDiagnosisSequence>(requestUrl),
  };
};

export const createEntity: ICrudPutAction<IDiagnosisSequence> = entity => async dispatch => {
  const result = await dispatch({
    type: ACTION_TYPES.CREATE_DIAGNOSISSEQUENCE,
    payload: axios.post(apiUrl, cleanEntity(entity)),
  });
  dispatch(getEntities());
  return result;
};

export const updateEntity: ICrudPutAction<IDiagnosisSequence> = entity => async dispatch => {
  const result = await dispatch({
    type: ACTION_TYPES.UPDATE_DIAGNOSISSEQUENCE,
    payload: axios.put(`${apiUrl}/${entity.id}`, cleanEntity(entity)),
  });
  return result;
};

export const partialUpdate: ICrudPutAction<IDiagnosisSequence> = entity => async dispatch => {
  const result = await dispatch({
    type: ACTION_TYPES.PARTIAL_UPDATE_DIAGNOSISSEQUENCE,
    payload: axios.patch(`${apiUrl}/${entity.id}`, cleanEntity(entity)),
  });
  return result;
};

export const deleteEntity: ICrudDeleteAction<IDiagnosisSequence> = id => async dispatch => {
  const requestUrl = `${apiUrl}/${id}`;
  const result = await dispatch({
    type: ACTION_TYPES.DELETE_DIAGNOSISSEQUENCE,
    payload: axios.delete(requestUrl),
  });
  dispatch(getEntities());
  return result;
};

export const reset = () => ({
  type: ACTION_TYPES.RESET,
});
