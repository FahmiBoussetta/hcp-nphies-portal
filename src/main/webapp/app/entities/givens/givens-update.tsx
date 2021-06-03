import React, { useState, useEffect } from 'react';
import { connect } from 'react-redux';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Row, Col, Label } from 'reactstrap';
import { AvFeedback, AvForm, AvGroup, AvInput, AvField } from 'availity-reactstrap-validation';
import { Translate, translate } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';
import { IRootState } from 'app/shared/reducers';

import { IHumanName } from 'app/shared/model/human-name.model';
import { getEntities as getHumanNames } from 'app/entities/human-name/human-name.reducer';
import { getEntity, updateEntity, createEntity, reset } from './givens.reducer';
import { IGivens } from 'app/shared/model/givens.model';
import { convertDateTimeFromServer, convertDateTimeToServer, displayDefaultDateTime } from 'app/shared/util/date-utils';
import { mapIdList } from 'app/shared/util/entity-utils';

export interface IGivensUpdateProps extends StateProps, DispatchProps, RouteComponentProps<{ id: string }> {}

export const GivensUpdate = (props: IGivensUpdateProps) => {
  const [isNew] = useState(!props.match.params || !props.match.params.id);

  const { givensEntity, humanNames, loading, updating } = props;

  const handleClose = () => {
    props.history.push('/givens');
  };

  useEffect(() => {
    if (isNew) {
      props.reset();
    } else {
      props.getEntity(props.match.params.id);
    }

    props.getHumanNames();
  }, []);

  useEffect(() => {
    if (props.updateSuccess) {
      handleClose();
    }
  }, [props.updateSuccess]);

  const saveEntity = (event, errors, values) => {
    if (errors.length === 0) {
      const entity = {
        ...givensEntity,
        ...values,
        human: humanNames.find(it => it.id.toString() === values.humanId.toString()),
      };

      if (isNew) {
        props.createEntity(entity);
      } else {
        props.updateEntity(entity);
      }
    }
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
            <AvForm model={isNew ? {} : givensEntity} onSubmit={saveEntity}>
              {!isNew ? (
                <AvGroup>
                  <Label for="givens-id">
                    <Translate contentKey="global.field.id">ID</Translate>
                  </Label>
                  <AvInput id="givens-id" type="text" className="form-control" name="id" required readOnly />
                </AvGroup>
              ) : null}
              <AvGroup>
                <Label id="givenLabel" for="givens-given">
                  <Translate contentKey="hcpNphiesPortalApp.givens.given">Given</Translate>
                </Label>
                <AvField
                  id="givens-given"
                  data-cy="given"
                  type="text"
                  name="given"
                  validate={{
                    required: { value: true, errorMessage: translate('entity.validation.required') },
                  }}
                />
              </AvGroup>
              <AvGroup>
                <Label id="prefixLabel" for="givens-prefix">
                  <Translate contentKey="hcpNphiesPortalApp.givens.prefix">Prefix</Translate>
                </Label>
                <AvField id="givens-prefix" data-cy="prefix" type="text" name="prefix" />
              </AvGroup>
              <AvGroup>
                <Label id="suffixLabel" for="givens-suffix">
                  <Translate contentKey="hcpNphiesPortalApp.givens.suffix">Suffix</Translate>
                </Label>
                <AvField id="givens-suffix" data-cy="suffix" type="text" name="suffix" />
              </AvGroup>
              <AvGroup>
                <Label id="textNameLabel" for="givens-textName">
                  <Translate contentKey="hcpNphiesPortalApp.givens.textName">Text Name</Translate>
                </Label>
                <AvField id="givens-textName" data-cy="textName" type="text" name="textName" />
              </AvGroup>
              <AvGroup>
                <Label for="givens-human">
                  <Translate contentKey="hcpNphiesPortalApp.givens.human">Human</Translate>
                </Label>
                <AvInput id="givens-human" data-cy="human" type="select" className="form-control" name="humanId">
                  <option value="" key="0" />
                  {humanNames
                    ? humanNames.map(otherEntity => (
                        <option value={otherEntity.id} key={otherEntity.id}>
                          {otherEntity.id}
                        </option>
                      ))
                    : null}
                </AvInput>
              </AvGroup>
              <Button tag={Link} id="cancel-save" to="/givens" replace color="info">
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
            </AvForm>
          )}
        </Col>
      </Row>
    </div>
  );
};

const mapStateToProps = (storeState: IRootState) => ({
  humanNames: storeState.humanName.entities,
  givensEntity: storeState.givens.entity,
  loading: storeState.givens.loading,
  updating: storeState.givens.updating,
  updateSuccess: storeState.givens.updateSuccess,
});

const mapDispatchToProps = {
  getHumanNames,
  getEntity,
  updateEntity,
  createEntity,
  reset,
};

type StateProps = ReturnType<typeof mapStateToProps>;
type DispatchProps = typeof mapDispatchToProps;

export default connect(mapStateToProps, mapDispatchToProps)(GivensUpdate);
