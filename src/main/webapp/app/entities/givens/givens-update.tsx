import React, { useState, useEffect } from 'react';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Row, Col, FormText } from 'reactstrap';
import { isNumber, Translate, translate, ValidatedField, ValidatedForm } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { IHumanName } from 'app/shared/model/human-name.model';
import { getEntities as getHumanNames } from 'app/entities/human-name/human-name.reducer';
import { getEntity, updateEntity, createEntity, reset } from './givens.reducer';
import { IGivens } from 'app/shared/model/givens.model';
import { convertDateTimeFromServer, convertDateTimeToServer, displayDefaultDateTime } from 'app/shared/util/date-utils';
import { mapIdList } from 'app/shared/util/entity-utils';
import { useAppDispatch, useAppSelector } from 'app/config/store';

export const GivensUpdate = (props: RouteComponentProps<{ id: string }>) => {
  const dispatch = useAppDispatch();

  const [isNew] = useState(!props.match.params || !props.match.params.id);

  const humanNames = useAppSelector(state => state.humanName.entities);
  const givensEntity = useAppSelector(state => state.givens.entity);
  const loading = useAppSelector(state => state.givens.loading);
  const updating = useAppSelector(state => state.givens.updating);
  const updateSuccess = useAppSelector(state => state.givens.updateSuccess);

  const handleClose = () => {
    props.history.push('/givens');
  };

  useEffect(() => {
    if (isNew) {
      dispatch(reset());
    } else {
      dispatch(getEntity(props.match.params.id));
    }

    dispatch(getHumanNames({}));
  }, []);

  useEffect(() => {
    if (updateSuccess) {
      handleClose();
    }
  }, [updateSuccess]);

  const saveEntity = values => {
    const entity = {
      ...givensEntity,
      ...values,
      human: humanNames.find(it => it.id.toString() === values.humanId.toString()),
    };

    if (isNew) {
      dispatch(createEntity(entity));
    } else {
      dispatch(updateEntity(entity));
    }
  };

  const defaultValues = () =>
    isNew
      ? {}
      : {
          ...givensEntity,
          humanId: givensEntity?.human?.id,
        };

  return (
    <div>
      <Row className="justify-content-center">
        <Col md="8">
          <h2 id="hcpNphiesPortalApp.givens.home.createOrEditLabel" data-cy="GivensCreateUpdateHeading">
            <Translate contentKey="hcpNphiesPortalApp.givens.home.createOrEditLabel">Create or edit a Givens</Translate>
          </h2>
        </Col>
      </Row>
      <Row className="justify-content-center">
        <Col md="8">
          {loading ? (
            <p>Loading...</p>
          ) : (
            <ValidatedForm defaultValues={defaultValues()} onSubmit={saveEntity}>
              {!isNew ? (
                <ValidatedField
                  name="id"
                  required
                  readOnly
                  id="givens-id"
                  label={translate('global.field.id')}
                  validate={{ required: true }}
                />
              ) : null}
              <ValidatedField
                label={translate('hcpNphiesPortalApp.givens.given')}
                id="givens-given"
                name="given"
                data-cy="given"
                type="text"
                validate={{
                  required: { value: true, message: translate('entity.validation.required') },
                }}
              />
              <ValidatedField
                label={translate('hcpNphiesPortalApp.givens.prefix')}
                id="givens-prefix"
                name="prefix"
                data-cy="prefix"
                type="text"
              />
              <ValidatedField
                label={translate('hcpNphiesPortalApp.givens.suffix')}
                id="givens-suffix"
                name="suffix"
                data-cy="suffix"
                type="text"
              />
              <ValidatedField
                label={translate('hcpNphiesPortalApp.givens.textName')}
                id="givens-textName"
                name="textName"
                data-cy="textName"
                type="text"
              />
              <ValidatedField
                id="givens-human"
                name="humanId"
                data-cy="human"
                label={translate('hcpNphiesPortalApp.givens.human')}
                type="select"
              >
                <option value="" key="0" />
                {humanNames
                  ? humanNames.map(otherEntity => (
                      <option value={otherEntity.id} key={otherEntity.id}>
                        {otherEntity.id}
                      </option>
                    ))
                  : null}
              </ValidatedField>
              <Button tag={Link} id="cancel-save" data-cy="entityCreateCancelButton" to="/givens" replace color="info">
                <FontAwesomeIcon icon="arrow-left" />
                &nbsp;
                <span className="d-none d-md-inline">
                  <Translate contentKey="entity.action.back">Back</Translate>
                </span>
              </Button>
              &nbsp;
              <Button color="primary" id="save-entity" data-cy="entityCreateSaveButton" type="submit" disabled={updating}>
                <FontAwesomeIcon icon="save" />
                &nbsp;
                <Translate contentKey="entity.action.save">Save</Translate>
              </Button>
            </ValidatedForm>
          )}
        </Col>
      </Row>
    </div>
  );
};

export default GivensUpdate;
