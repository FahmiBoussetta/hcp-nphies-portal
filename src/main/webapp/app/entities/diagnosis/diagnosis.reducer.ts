import axios from 'axios';
import { ICrudGetAction, ICrudGetAllAction, ICrudPutAction, ICrudDeleteAction } from 'react-jhipster';

import { cleanEntity } from 'app/shared/util/entity-utils';
import { REQUEST, SUCCESS, FAILURE } from 'app/shared/reducers/action-type.util';

import { IDiagnosis, defaultValue } from 'app/shared/model/diagnosis.model';

export const ACTION_TYPES = {
  FETCH_DIAGNOSIS_LIST: 'diagnosis/FETCH_DIAGNOSIS_LIST',
  FETCH_DIAGNOSIS: 'diagnosis/FETCH_DIAGNOSIS',
  CREATE_DIAGNOSIS: 'diagnosis/CREATE_DIAGNOSIS',
  UPDATE_DIAGNOSIS: 'diagnosis/UPDATE_DIAGNOSIS',
  PARTIAL_UPDATE_DIAGNOSIS: 'diagnosis/PARTIAL_UPDATE_DIAGNOSIS',
  DELETE_DIAGNOSIS: 'diagnosis/DELETE_DIAGNOSIS',
  RESET: 'diagnosis/RESET',
};

const initialState = {
  loading: false,
  errorMessage: null,
  entities: [] as ReadonlyArray<IDiagnosis>,
  entity: defaultValue,
  updating: false,
  updateSuccess: false,
};

export type DiagnosisState = Readonly<typeof initialState>;

// Reducer

export default (state: DiagnosisState = initialState, action): DiagnosisState => {
  switch (action.type) {
    case REQUEST(ACTION_TYPES.FETCH_DIAGNOSIS_LIST):
    case REQUEST(ACTION_TYPES.FETCH_DIAGNOSIS):
      return {
        ...state,
        errorMessage: null,
        updateSuccess: false,
        loading: true,
      };
    case REQUEST(ACTION_TYPES.CREATE_DIAGNOSIS):
    case REQUEST(ACTION_TYPES.UPDATE_DIAGNOSIS):
    case REQUEST(ACTION_TYPES.DELETE_DIAGNOSIS):
    case REQUEST(ACTION_TYPES.PARTIAL_UPDATE_DIAGNOSIS):
      return {
        ...state,
        errorMessage: null,
        updateSuccess: false,
        updating: true,
      };
    case FAILURE(ACTION_TYPES.FETCH_DIAGNOSIS_LIST):
    case FAILURE(ACTION_TYPES.FETCH_DIAGNOSIS):
    case FAILURE(ACTION_TYPES.CREATE_DIAGNOSIS):
    case FAILURE(ACTION_TYPES.UPDATE_DIAGNOSIS):
    case FAILURE(ACTION_TYPES.PARTIAL_UPDATE_DIAGNOSIS):
    case FAILURE(ACTION_TYPES.DELETE_DIAGNOSIS):
      return {
        ...state,
        loading: false,
        updating: false,
        updateSuccess: false,
        errorMessage: action.payload,
      };
    case SUCCESS(ACTION_TYPES.FETCH_DIAGNOSIS_LIST):
      return {
        ...state,
        loading: false,
        entities: action.payload.data,
      };
    case SUCCESS(ACTION_TYPES.FETCH_DIAGNOSIS):
      return {
        ...state,
        loading: false,
        entity: action.payload.data,
      };
    case SUCCESS(ACTION_TYPES.CREATE_DIAGNOSIS):
    case SUCCESS(ACTION_TYPES.UPDATE_DIAGNOSIS):
    case SUCCESS(ACTION_TYPES.PARTIAL_UPDATE_DIAGNOSIS):
      return {
        ...state,
        updating: false,
        updateSuccess: true,
        entity: action.payload.data,
      };
    case SUCCESS(ACTION_TYPES.DELETE_DIAGNOSIS):
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

const apiUrl = 'api/diagnoses';

// Actions

export const getEntities: ICrudGetAllAction<IDiagnosis> = (page, size, sort) => ({
  type: ACTION_TYPES.FETCH_DIAGNOSIS_LIST,
  payload: axios.get<IDiagnosis>(`${apiUrl}?cacheBuster=${new Date().getTime()}`),
});

export const getEntity: ICrudGetAction<IDiagnosis> = id => {
  const requestUrl = `${apiUrl}/${id}`;
  return {
    type: ACTION_TYPES.FETCH_DIAGNOSIS,
    payload: axios.get<IDiagnosis>(requestUrl),
  };
};

export const createEntity: ICrudPutAction<IDiagnosis> = entity => async dispatch => {
  const result = await dispatch({
    type: ACTION_TYPES.CREATE_DIAGNOSIS,
    payload: axios.post(apiUrl, cleanEntity(entity)),
  });
  dispatch(getEntities());
  return result;
};

export const updateEntity: ICrudPutAction<IDiagnosis> = entity => async dispatch => {
  const result = await dispatch({
    type: ACTION_TYPES.UPDATE_DIAGNOSIS,
    payload: axios.put(`${apiUrl}/${entity.id}`, cleanEntity(entity)),
  });
  return result;
};

export const partialUpdate: ICrudPutAction<IDiagnosis> = entity => async dispatch => {
  const result = await dispatch({
    type: ACTION_TYPES.PARTIAL_UPDATE_DIAGNOSIS,
    payload: axios.patch(`${apiUrl}/${entity.id}`, cleanEntity(entity)),
  });
  return result;
};

export const deleteEntity: ICrudDeleteAction<IDiagnosis> = id => async dispatch => {
  const requestUrl = `${apiUrl}/${id}`;
  const result = await dispatch({
    type: ACTION_TYPES.DELETE_DIAGNOSIS,
    payload: axios.delete(requestUrl),
  });
  dispatch(getEntities());
  return result;
};

export const reset = () => ({
  type: ACTION_TYPES.RESET,
});
