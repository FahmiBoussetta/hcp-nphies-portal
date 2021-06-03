import axios from 'axios';
import { ICrudGetAction, ICrudGetAllAction, ICrudPutAction, ICrudDeleteAction } from 'react-jhipster';

import { cleanEntity } from 'app/shared/util/entity-utils';
import { REQUEST, SUCCESS, FAILURE } from 'app/shared/reducers/action-type.util';

import { IPractitionerRole, defaultValue } from 'app/shared/model/practitioner-role.model';

export const ACTION_TYPES = {
  FETCH_PRACTITIONERROLE_LIST: 'practitionerRole/FETCH_PRACTITIONERROLE_LIST',
  FETCH_PRACTITIONERROLE: 'practitionerRole/FETCH_PRACTITIONERROLE',
  CREATE_PRACTITIONERROLE: 'practitionerRole/CREATE_PRACTITIONERROLE',
  UPDATE_PRACTITIONERROLE: 'practitionerRole/UPDATE_PRACTITIONERROLE',
  PARTIAL_UPDATE_PRACTITIONERROLE: 'practitionerRole/PARTIAL_UPDATE_PRACTITIONERROLE',
  DELETE_PRACTITIONERROLE: 'practitionerRole/DELETE_PRACTITIONERROLE',
  RESET: 'practitionerRole/RESET',
};

const initialState = {
  loading: false,
  errorMessage: null,
  entities: [] as ReadonlyArray<IPractitionerRole>,
  entity: defaultValue,
  updating: false,
  updateSuccess: false,
};

export type PractitionerRoleState = Readonly<typeof initialState>;

// Reducer

export default (state: PractitionerRoleState = initialState, action): PractitionerRoleState => {
  switch (action.type) {
    case REQUEST(ACTION_TYPES.FETCH_PRACTITIONERROLE_LIST):
    case REQUEST(ACTION_TYPES.FETCH_PRACTITIONERROLE):
      return {
        ...state,
        errorMessage: null,
        updateSuccess: false,
        loading: true,
      };
    case REQUEST(ACTION_TYPES.CREATE_PRACTITIONERROLE):
    case REQUEST(ACTION_TYPES.UPDATE_PRACTITIONERROLE):
    case REQUEST(ACTION_TYPES.DELETE_PRACTITIONERROLE):
    case REQUEST(ACTION_TYPES.PARTIAL_UPDATE_PRACTITIONERROLE):
      return {
        ...state,
        errorMessage: null,
        updateSuccess: false,
        updating: true,
      };
    case FAILURE(ACTION_TYPES.FETCH_PRACTITIONERROLE_LIST):
    case FAILURE(ACTION_TYPES.FETCH_PRACTITIONERROLE):
    case FAILURE(ACTION_TYPES.CREATE_PRACTITIONERROLE):
    case FAILURE(ACTION_TYPES.UPDATE_PRACTITIONERROLE):
    case FAILURE(ACTION_TYPES.PARTIAL_UPDATE_PRACTITIONERROLE):
    case FAILURE(ACTION_TYPES.DELETE_PRACTITIONERROLE):
      return {
        ...state,
        loading: false,
        updating: false,
        updateSuccess: false,
        errorMessage: action.payload,
      };
    case SUCCESS(ACTION_TYPES.FETCH_PRACTITIONERROLE_LIST):
      return {
        ...state,
        loading: false,
        entities: action.payload.data,
      };
    case SUCCESS(ACTION_TYPES.FETCH_PRACTITIONERROLE):
      return {
        ...state,
        loading: false,
        entity: action.payload.data,
      };
    case SUCCESS(ACTION_TYPES.CREATE_PRACTITIONERROLE):
    case SUCCESS(ACTION_TYPES.UPDATE_PRACTITIONERROLE):
    case SUCCESS(ACTION_TYPES.PARTIAL_UPDATE_PRACTITIONERROLE):
      return {
        ...state,
        updating: false,
        updateSuccess: true,
        entity: action.payload.data,
      };
    case SUCCESS(ACTION_TYPES.DELETE_PRACTITIONERROLE):
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

const apiUrl = 'api/practitioner-roles';

// Actions

export const getEntities: ICrudGetAllAction<IPractitionerRole> = (page, size, sort) => ({
  type: ACTION_TYPES.FETCH_PRACTITIONERROLE_LIST,
  payload: axios.get<IPractitionerRole>(`${apiUrl}?cacheBuster=${new Date().getTime()}`),
});

export const getEntity: ICrudGetAction<IPractitionerRole> = id => {
  const requestUrl = `${apiUrl}/${id}`;
  return {
    type: ACTION_TYPES.FETCH_PRACTITIONERROLE,
    payload: axios.get<IPractitionerRole>(requestUrl),
  };
};

export const createEntity: ICrudPutAction<IPractitionerRole> = entity => async dispatch => {
  const result = await dispatch({
    type: ACTION_TYPES.CREATE_PRACTITIONERROLE,
    payload: axios.post(apiUrl, cleanEntity(entity)),
  });
  dispatch(getEntities());
  return result;
};

export const updateEntity: ICrudPutAction<IPractitionerRole> = entity => async dispatch => {
  const result = await dispatch({
    type: ACTION_TYPES.UPDATE_PRACTITIONERROLE,
    payload: axios.put(`${apiUrl}/${entity.id}`, cleanEntity(entity)),
  });
  return result;
};

export const partialUpdate: ICrudPutAction<IPractitionerRole> = entity => async dispatch => {
  const result = await dispatch({
    type: ACTION_TYPES.PARTIAL_UPDATE_PRACTITIONERROLE,
    payload: axios.patch(`${apiUrl}/${entity.id}`, cleanEntity(entity)),
  });
  return result;
};

export const deleteEntity: ICrudDeleteAction<IPractitionerRole> = id => async dispatch => {
  const requestUrl = `${apiUrl}/${id}`;
  const result = await dispatch({
    type: ACTION_TYPES.DELETE_PRACTITIONERROLE,
    payload: axios.delete(requestUrl),
  });
  dispatch(getEntities());
  return result;
};

export const reset = () => ({
  type: ACTION_TYPES.RESET,
});
